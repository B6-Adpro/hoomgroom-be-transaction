package hoomgroom.transaction.pengiriman.model;

class ProcessingState extends State {

    public ProcessingState(Pengiriman pengiriman){
        super(pengiriman);
    }

    @Override
    public void startProcessing() {
        //TODO process
        pengiriman.setStatus("SEDANG_DIKEMAS");
        pengiriman.changeStatus(new PackagingState(pengiriman));
    }

    @Override
    public void finishPackaging() {
        //TODO packaging, do nothing
    }

    @Override
    public void startShipping() {
        //TODO shipping, do nothing
    }

    @Override
    public void arrive() {
        //TODO notify arrival, do nothing
    }
}