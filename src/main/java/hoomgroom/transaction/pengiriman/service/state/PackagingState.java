package hoomgroom.transaction.pengiriman.service.state;

public class PackagingState implements PengirimanState {
    @Override
    public void pengirimanInfo() {
        System.out.println("Pengiriman sedang dikemas.");
    }
}