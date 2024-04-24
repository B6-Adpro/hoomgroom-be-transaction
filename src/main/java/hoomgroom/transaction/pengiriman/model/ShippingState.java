package hoomgroom.transaction.pengiriman.model;

class ShippingState extends State {
    
    public ShippingState(Pengiriman pengiriman){
        super(pengiriman);
    }

    @Override
    public void startProcessing() {
        //do nothing
    }

    @Override
    public void finishPackaging() {
        //do nothing
    }

    @Override
    public void startShipping() {
        pengiriman.setStatus("SEDANG_DIKIRIM");
        pengiriman.changeStatus(new ArriveState(pengiriman));
    }

    @Override
    public void arrive() {
        //do nothing
    }
}
