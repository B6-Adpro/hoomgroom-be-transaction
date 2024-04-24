package hoomgroom.transaction.pengiriman.model;

class PackagingState extends State {

    public PackagingState(Pengiriman pengiriman){
        super(pengiriman);
    }

    @Override
    public void startProcessing() {
        //do nothing
    }

    @Override
    public void finishPackaging() {
        pengiriman.setStatus("SEDANG_DIKIRIM");
        pengiriman.changeStatus(new ShippingState(pengiriman));
    }

    @Override
    public void startShipping() {
        //do nothing
    }

    @Override
    public void arrive() {
        //do nothing
    }
}