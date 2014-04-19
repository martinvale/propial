Ext.define('Propial.view.panel.ImageGallery', {
  extend: 'Ext.panel.Panel',
  requires: [
    'Propial.store.Resources'
  ],
  alias: 'widget.imagegallery',
  cls: 'images-viewer',
  buttonAlign: 'left',
  initComponent : function() {
    var me = this;

    me.editor = Ext.create('Propial.view.form.UploadPhotoForm', {
      url: me.uploadUrl,
      listeners: {
        onUploaded: function (form, resource) {
          me.getDataView().getStore().add(resource);
          form.hide();
        },
        onClosed: function (form) {
          form.hide();
        }
      }
    });

    me.fbar = [
      {
        xtype: 'button',
        text: 'izquierda',
        icon: '../images/arrow-left.png',
        handler: function (b, e) {
          var dvImages = me.getDataView();
          var aSelectedImage = dvImages.getSelectionModel().getSelection();
          for (var i = 0; i < aSelectedImage.length; i++) {
            var index = dvImages.getStore().indexOf(aSelectedImage[i]);
            if (index > 0) {
              var resource = aSelectedImage[i];
              var position = index - 1;
              Ext.Ajax.request({
                url: '/services/publications/' + me.publicationId + '/' + resource.get('key') + '/move',
                params: {
                  position: position
                },
                success: function(response){
                  dvImages.getStore().remove(resource);
                  dvImages.getStore().insert(position, resource);
                  me.fireEvent ('onMoved', dvImages, resource, position);
                }
              });
            }
          }
        }
      }, {
        xtype: 'button',
        text: 'derecha',
        icon: '../images/arrow-right.png',
        handler: function (b, e) {
          var dvImages = me.getDataView();
          var aSelectedImage = dvImages.getSelectionModel().getSelection();
          for (var i = 0; i < aSelectedImage.length; i++) {
            var index = dvImages.getStore().indexOf(aSelectedImage[i]);
            if (index < dvImages.getStore().getCount() - 1) {
              var resource = aSelectedImage[i];
              var position = index + 1;
              Ext.Ajax.request({
                url: '/services/publications/' + me.publicationId + '/' + resource.get('key') + '/move',
                params: {
                  position: position
                },
                success: function(response){
                  dvImages.getStore().remove(resource);
                  dvImages.getStore().insert(position, resource);
                  me.fireEvent ('onMoved', dvImages, resource, position);
                }
              });
            }
          }
        }
      }, {
        xtype: 'button',
        icon: '../images/arrow-up.png',
        text: 'Agregar una imagen',
        handler: function (b, e) {
          me.editor.show();
        }
      }, {
        xtype: 'button',
        text: 'Eliminar',
        handler: function (b, e) {
          var dvImages = me.getDataView();
          var aSelectedImage = dvImages.getSelectionModel().getSelection();
          for (var i = 0; i < aSelectedImage.length; i++) {
            var key = aSelectedImage[i].get('key');
            var resource = aSelectedImage[i];
            Ext.Ajax.request({
              url: '/services/publications/' + me.publicationId + '/' + key,
              method: 'DELETE',
              success: function(response){
                dvImages.getStore().remove(resource);
                //dvImages.refresh();
                me.fireEvent ('onDeleted', resource);
              }
            });
          }
        }
      }
    ];
    me.items = [Ext.create('Ext.view.View', {
      store: Ext.create('Propial.store.Resources'),
      cls: 'image-gallery',
      tpl: new Ext.XTemplate(
        '<tpl for=".">',
          '<div class="thumb-wrap">',
            '<div class="thumb">',
              '<img src="{[this.getDir(values)]}" title="{key}" />',
            '</div>',
          '</div>',
        '</tpl>',
        {
          getDir: function (values) {
            /*var url = null;
            if (values.id) {
              url = me.getUploadDir() + '/images/small_' + values.nombre;
              if (values.tipo == 'youtube') {
                url = values.thumbnail;
              }
            } else {
              url = me.getTempDir() + '/small_' + values.nombre;
            }*/
            return '/services/publications/resource/' + values.key;
          }
        }
      ),
      height: 355,
      trackOver: true,
      overItemCls: 'x-item-over',
      itemSelector: 'div.thumb-wrap',
      emptyText: 'No hay im&aacute;genes para mostrar'
    }), me.editor];
    
    me.callParent();
    
    this.addEvents ('onMoved', 'onDeleted');
  },
  getDataView: function() {
    return this.down('dataview');
  },
  loadImages: function(publication) {
    var me = this;
    me.publicationId = publication.get('id');

    this.editor.objectId = me.publicationId;
    var form = this.editor.getForm();
    form.reset();
    form.setValues({publicationId: me.publicationId});

    publication.resources().each(function (resource) {
      me.getDataView().getStore().add(resource);
    });
  }
});
