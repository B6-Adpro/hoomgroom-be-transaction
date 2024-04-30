package hoomgroom.transaction.pengiriman.model;

abstract class State {
    protected Pengiriman pengiriman;

    public State(Pengiriman pengiriman) {
        this.pengiriman = pengiriman;
    }

    abstract void startProcessing();
    abstract void finishPackaging();
    abstract void startShipping();
    abstract void arrive();
}
