package hoomgroom.transaction.pengiriman.service.State;

public class ProcessingState implements PengirimanState {
    @Override
    public void pengirimanInfo() {
        System.out.println("Pengiriman sedang diproses.");
    }
}