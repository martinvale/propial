Ext.define('Propial.view.form.field.Image', {
  extend: 'Ext.form.field.Base',
  alias: 'widget.imagefield',
  inputType: 'file',
  rootUrl: '/resources/',
  //layout: 'hbox',
  initComponent: function () {
    this.callParent(arguments);
  },
  onRender: function(field, opts) {
    this.callParent(arguments);
    var me = this;
    var changeEl = {
      tag: 'button',
      html: 'cambiar'
    };
    this.change = this.bodyEl.insertFirst(changeEl);
    this.change.setVisibilityMode(Ext.Element.DISPLAY);
    this.change.hide();

    var imageEl = {tag: 'img', src: ''};
    if (this.imageWidth) {
      imageEl.width = this.imageWidth;
    }
    if (this.imageHeight) {
      imageEl.height = this.imageHeight;
    }
    this.image = this.bodyEl.insertFirst(imageEl);
    this.image.setVisibilityMode(Ext.Element.DISPLAY);
    this.image.hide();

    this.change.on('click', function() {
      me.image.hide();
      me.change.hide();
      me.inputEl.show();
    });
  },
  isFileUpload: function() {
    return true;
  },
  fireChange: function(e){
    this.fireEvent('change', this, e, this.inputEl.dom.value);
  },
  restoreInput: function(el){
    this.inputEl.remove();
    el = Ext.get(el);
    this.bodyEl.appendChild(el);
    this.inputEl = el;
    this.inputEl.on('change', this.fireChange, this);
  },
  extractFileInput: function() {
    var fileInput = this.inputEl.dom;
    this.reset();
    return fileInput;
  },
  reset : function(){
    var me = this;
    if (me.rendered) {
      me.inputEl.remove();
      me.inputEl = me.bodyEl.createChild({
        name: me.name,
        cls: me.inputCls,
        tag: 'input',
        type: 'file',
        size: 1
      });
      this.inputEl.on('change', this.fireChange, this);
      this.image.set({src: ''});
    }
    me.callParent();
  },
  setValue: function (value) {
    if (this.image) {
      if (value) {
        this.image.set({src: this.rootUrl + value});
        this.inputEl.hide();
        this.image.show();
        this.change.show();
      } else {
        this.image.hide();
        this.change.hide();
        this.inputEl.show();
      }
    }
    //this.callParent(value);
  }
});