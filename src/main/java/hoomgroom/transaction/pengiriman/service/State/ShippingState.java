package hoomgroom.transaction.pengiriman.service.State;

public class ShippingState implements PengirimanState {
    @Override
    public void pengirimanInfo() {
        System.out.println("Pengiriman sedang dikirim.");
    }
}
