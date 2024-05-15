package hoomgroom.transaction.dto;

import hoomgroom.transaction.pengiriman.dto.PengirimanData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PengirimanDataTest {

    private PengirimanData pengirimanData;

    @BeforeEach
    void setUp() {
        pengirimanData = new PengirimanData();
        pengirimanData.setId("1");
        pengirimanData.setTransaksiId("T123");
        pengirimanData.setAlamat("Some Address");
        pengirimanData.setFurniture("Some Furniture");
        pengirimanData.setStateString("DALAM_PROSES");
    }

    @Test
    void testPengirimanDataGettersAndSetters() {
        assertThat(pengirimanData.getId()).isEqualTo("1");
        assertThat(pengirimanData.getTransaksiId()).isEqualTo("T123");
        assertThat(pengirimanData.getAlamat()).isEqualTo("Some Address");
        assertThat(pengirimanData.getFurniture()).isEqualTo("Some Furniture");
        assertThat(pengirimanData.getStateString()).isEqualTo("DALAM_PROSES");
    }

    @Test
    void testPengirimanDataBuilder() {
        PengirimanData pengirimanDataFromBuilder = PengirimanData.builder()
                .id("1")
                .transaksiId("T123")
                .alamat("Some Address")
                .furniture("Some Furniture")
                .stateString("DALAM_PROSES")
                .build();

        assertThat(pengirimanDataFromBuilder.getId()).isEqualTo("1");
        assertThat(pengirimanDataFromBuilder.getTransaksiId()).isEqualTo("T123");
        assertThat(pengirimanDataFromBuilder.getAlamat()).isEqualTo("Some Address");
        assertThat(pengirimanDataFromBuilder.getFurniture()).isEqualTo("Some Furniture");
        assertThat(pengirimanDataFromBuilder.getStateString()).isEqualTo("DALAM_PROSES");
    }

    @Test
    void testPengirimanDataNoArgsConstructor() {
        PengirimanData emptyPengirimanData = new PengirimanData();

        assertThat(emptyPengirimanData.getId()).isNull();
        assertThat(emptyPengirimanData.getTransaksiId()).isNull();
        assertThat(emptyPengirimanData.getAlamat()).isNull();
        assertThat(emptyPengirimanData.getFurniture()).isNull();
        assertThat(emptyPengirimanData.getStateString()).isNull();
    }

    @Test
    void testPengirimanDataAllArgsConstructor() {
        PengirimanData pengirimanDataAllArgs = new PengirimanData("1", "T123", "Some Address", "Some Furniture", "DALAM_PROSES");

        assertThat(pengirimanDataAllArgs.getId()).isEqualTo("1");
        assertThat(pengirimanDataAllArgs.getTransaksiId()).isEqualTo("T123");
        assertThat(pengirimanDataAllArgs.getAlamat()).isEqualTo("Some Address");
        assertThat(pengirimanDataAllArgs.getFurniture()).isEqualTo("Some Furniture");
        assertThat(pengirimanDataAllArgs.getStateString()).isEqualTo("DALAM_PROSES");
    }

    @Test
    void testPengirimanDataToString() {
        String expectedString = "PengirimanData(id=1, transaksiId=T123, alamat=Some Address, furniture=Some Furniture, stateString=DALAM_PROSES)";

        assertThat(pengirimanData.toString()).isEqualTo(expectedString);
    }

    @Test
    void testPengirimanDataEqualsAndHashCode() {
        PengirimanData pengirimanData1 = new PengirimanData("1", "T123", "Some Address", "Some Furniture", "DALAM_PROSES");
        PengirimanData pengirimanData2 = new PengirimanData("1", "T123", "Some Address", "Some Furniture", "DALAM_PROSES");

        assertThat(pengirimanData1).isEqualTo(pengirimanData2);
        assertThat(pengirimanData1.hashCode()).isEqualTo(pengirimanData2.hashCode());
    }
}
