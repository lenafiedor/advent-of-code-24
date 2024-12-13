import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GardenerTest {

    @Test
    public void testCalculateWithDiscountSimple() {

        //given
        List<String> garden = List.of(
                "AAAA",
                "BBCD",
                "BBCC",
                "EEEC"
        );

        // when
        int priceWithDiscount = Gardener.calculatePrice(garden, true);

        // then
        assertEquals(80, priceWithDiscount);
    }

    @Test
    public void testCalculateWithDiscountInnerCirles() {

        //given
        List<String> garden = List.of(
                "AAAAAA",
                "AAABBA",
                "AAABBA",
                "ABBAAA",
                "ABBAAA",
                "AAAAAA"
        );

        // when
        int priceWithDiscount = Gardener.calculatePrice(garden, true);

        // then
        assertEquals(368, priceWithDiscount);
    }

    @Test
    public void testCalculateWithDiscount() {

        // given
        List<String> garden = Gardener.parseInput();

        // when
        int priceWithDiscount = Gardener.calculatePrice(garden, true);

        // then
        assertEquals(787680, priceWithDiscount);
    }
}
