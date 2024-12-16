import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FirstStarTest {

    public static AtomicInteger emptySpaces;

    @BeforeEach
    void setUp() {
        emptySpaces = new AtomicInteger(0);
    }

    @Test
    void testParseMap() {

        // given
        String input = "2333133121414131402";

        // when
        String parsed = FirstStar.parseMap(input, emptySpaces);

        // then
        assertEquals("00...111...2...333.44.5555.6666.777.888899", parsed);
    }

    @Test
    void testSqueezeMap() {
        // given
        String parsed = FirstStar.parseMap("2333133121414131402", emptySpaces);

        // when
        String squeezed = FirstStar.squeezeMap(parsed, emptySpaces);

        // then
       assertEquals("0099811188827773336446555566..............", squeezed);
    }

    @Test
    void testChecksum() {
        // given
        String squeezed = FirstStar.squeezeMap(FirstStar.parseMap("2333133121414131402", emptySpaces), emptySpaces);

        // when
        long checksum = FirstStar.checksum(squeezed);

        // then
        assertEquals(1928, checksum);
    }
}
