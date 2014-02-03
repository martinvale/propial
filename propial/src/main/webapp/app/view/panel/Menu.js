Ext.define('Propial.view.panel.Menu', {
  extend: 'Ext.panel.Panel',
  requires: [
    'Propial.store.Menues',
    'Ext.view.View'
  ],
  alias: 'widget.menu',
	items: [Ext.create('Ext.view.View', {
		store: Ext.create('Propial.store.Menues'),
		tpl: new Ext.XTemplate(
      '<tpl for=".">',
					'<a href="{url}">{label}</a>',
			'</tpl>',
			'<div class="x-clear"></div>'
		),
    overItemCls: 'x-item-over'
	})]
});
