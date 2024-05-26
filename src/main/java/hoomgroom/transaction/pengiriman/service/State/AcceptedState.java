package hoomgroom.transaction.pengiriman.service.State;

public class AcceptedState implements PengirimanState {
    @Override
    public void pengirimanInfo() {
        System.out.println("Pengiriman telah Diterima! Terima kasih telah menggunakan apliaksi HoomGroom..");
    }
}