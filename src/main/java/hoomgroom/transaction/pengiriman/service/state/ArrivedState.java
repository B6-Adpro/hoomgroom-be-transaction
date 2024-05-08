package hoomgroom.transaction.pengiriman.service.state;

public class ArrivedState implements PengirimanState {
    @Override
    public void pengirimanInfo() {
        System.out.println("Pengiriman telah sampai.");
    }
}