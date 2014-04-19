Ext.define('Propial.controller.Contract', {
    extend: 'Ext.app.Controller',

    refs: [{
      ref: 'contractsList',
      selector: 'contractslist'
    }],

    stores: ['Contracts'],
    
    init: function() {
        // Start listening for events on views
        this.control({
            'contractslist': {
                selectionchange: this.onContractSelect
            }
        });
    },
    
    onLaunch: function() {
        var contractsStore = this.getContractsStore();        
        contractsStore.load({
            callback: this.onContractsLoad,
            scope: this
        });
    },

    onContractsLoad: function() {
        var contractsList = this.getContractsList();
        contractsList.getSelectionModel().select(0);
    },
    
    onContractSelect: function(selModel, selection) {
        // Fire an application wide event
        this.application.fireEvent('stationstart', selection[0]);
    },
    
    /*onNewStationSelect: function(field, selection) {
        var selected = selection[0],
            store = this.getStationsStore(),
            list = this.getStationsList();
            
        if (selected && !store.getById(selected.get('id'))) {
            store.add(selected);
        }
        list.getSelectionModel().select(selected);
    }*/
});
