package com.ibiscus.propial.application.business;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.Validate;

import com.ibiscus.propial.domain.security.Contract;
import com.ibiscus.propial.domain.security.ContractRepository;
import com.ibiscus.propial.domain.security.User;
import com.ibiscus.propial.domain.security.UserRepository;

public class RegistrationService {

  private final String NO_REPLY_ADDRESS = "no-reply@nidus.com";

  private final UserRepository userRepository;

  private final ContractRepository contractRepository;

  private final String siteUrl;

  public RegistrationService(final UserRepository theUserRepository,
      final ContractRepository theContractRepository,
      final String theSiteUrl) {
    Validate.notNull(theUserRepository, "The user repository cannot be null");
    Validate.notNull(theContractRepository, "The contract repository cannot be "
        + "null");
    Validate.notNull(theSiteUrl, "The URL of the site cannot be null");
    userRepository = theUserRepository;
    contractRepository = theContractRepository;
    siteUrl = theSiteUrl;
  }

  private String getUserHash (final User user, final int expiration) throws NoSuchAlgorithmException, UnsupportedEncodingException {
    final StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append(user.getNickname());
    stringBuilder.append(user.getDisplayName());
    stringBuilder.append(user.getEmail());
    Calendar aCalendar = Calendar.getInstance();
    aCalendar.add(Calendar.HOUR_OF_DAY, 0);
    aCalendar.set(Calendar.MINUTE, 0);
    aCalendar.set(Calendar.SECOND, 0);
    aCalendar.add(Calendar.DAY_OF_MONTH, expiration);
    SimpleDateFormat df = new SimpleDateFormat();
    df.applyPattern("MM-dd-yyyy");
    stringBuilder.append(df.format(aCalendar.getTime()));

    final byte[] defaultBytes = stringBuilder.toString().getBytes();
    MessageDigest algorithm = MessageDigest.getInstance("MD5");
    // algorithm.reset();
    algorithm.update(defaultBytes);
    String code = (new Base64()).encodeToString(defaultBytes);
    //(new BASE64Encoder()).encode(algorithm.digest());
    code = URLDecoder.decode(code, "ISO-8859-1");
    code = code.replace(' ', '-').replace('=', '-');
    return code;
  }

  public void register(final User user, final Contract contract) {
    Properties props = new Properties();
    Session session = Session.getDefaultInstance(props, null);
    try {
      //Prepare the body of the message
      String msgBody = "Bienvenido!<br>";
      msgBody += "Ya puede comenzar a usar su cuenta.";

      Message msg = new MimeMessage(session);
      msg.setFrom(new InternetAddress(NO_REPLY_ADDRESS,
          "Nidus"));
      msg.addRecipient(Message.RecipientType.TO,
          new InternetAddress(user.getEmail(), user.getDisplayName()));
      msg.setSubject("Confirmación de la registración");
      msg.setText(msgBody);
      Transport.send(msg);
    } catch (AddressException e) {
      throw new RuntimeException("Cannot send mail", e);
    } catch (MessagingException e) {
      throw new RuntimeException("Cannot send mail", e);
    } catch (UnsupportedEncodingException e) {
      throw new RuntimeException("Cannot send mail", e);
    }
    long contractId = contractRepository.save(contract);
    Contract userContract = new Contract(contractId, contract.getType(),
        contract.getName());
    user.updateContract(userContract);
    userRepository.save(user);
  }

  public boolean confirm(final User user, final String hash) {
    try {
      int iOffset = 0;
      int iAttempt = 3;
      String aNewHash;
      aNewHash = getUserHash(user, iOffset);
      boolean bolIsEqual = MessageDigest.isEqual(aNewHash.getBytes(), URLDecoder.decode(hash, "ISO-8859-1").replace(' ', '-').replace('=', '-').getBytes());
      while (!bolIsEqual && iAttempt > 0) {
        iAttempt = iAttempt - 1;
        iOffset++;
        aNewHash = getUserHash(user, iOffset);
        bolIsEqual = MessageDigest.isEqual(aNewHash.getBytes(), URLDecoder.decode(hash, "ISO-8859-1").replace(' ', '-').replace('=', '-').getBytes());
      }
      if (bolIsEqual) {
        user.confirm();
        userRepository.save(user);
      }
      return bolIsEqual;
    } catch (UnsupportedEncodingException e) {
      return false;
    } catch (NoSuchAlgorithmException e) {
      return false;
    }
  }

}
