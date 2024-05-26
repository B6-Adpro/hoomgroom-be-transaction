package hoomgroom.transaction.pengiriman.model;

class ArriveState extends State {

    public ArriveState(Pengiriman pengiriman){
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
        //do nothing
    }

    @Override
    public void arrive() {
        pengiriman.setStatus("DITERIMA");
    }
}