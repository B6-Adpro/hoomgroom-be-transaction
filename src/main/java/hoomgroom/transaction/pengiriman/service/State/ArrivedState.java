package hoomgroom.transaction.pengiriman.service.State;

public class ArrivedState implements PengirimanState {
    @Override
    public void pengirimanInfo() {
        System.out.println("Pengiriman telah sampai.");
    }
}