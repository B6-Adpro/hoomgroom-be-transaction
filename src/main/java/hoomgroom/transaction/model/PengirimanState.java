package hoomgroom.transaction.model;

abstract class PengirimanState {
    protected Pengiriman pengiriman;

    public PengirimanState(Pengiriman pengiriman) {
        this.pengiriman = pengiriman;
    }
}
