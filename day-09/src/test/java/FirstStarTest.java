import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FirstStarTest {

    public static List<Integer> emptySpaces;

    @BeforeEach
    void setUp() {
        emptySpaces = new ArrayList<>();
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
    void testParseTwoDigits() {

        // given
        String input = "23331331214141314023581403";

        // when
        String parsed = FirstStar.parseMap(input, emptySpaces);

        // then
        assertEquals("00...111...2...333.44.5555.6666.777.888899...1010101010................11..............", parsed);
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
    void testSqueezeTwoDigits() {

        // given
        String parsed = FirstStar.parseMap("23331331214141314023581403", emptySpaces);

        // when
        String squeezed = FirstStar.squeezeMap(parsed, emptySpaces);

        // then
        assertEquals("0011111101021013330441555506666977798888...............................................", squeezed);
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

    @Test
    void testChecksumTwoDigits() {

        // given
        String squeezed = FirstStar.squeezeMap(FirstStar.parseMap("23331331214141314023581403", emptySpaces), emptySpaces);

        // when
        long checksum = FirstStar.checksum(squeezed);

        // then
        assertEquals(3385, checksum);
    }

    @Test
    void testFinalChecksum() {

        // given
        String squeezed = FirstStar.squeezeMap(FirstStar.parseMap(Objects.requireNonNull(FirstStar.parseInput()), emptySpaces), emptySpaces);

        // when
        long checksum = FirstStar.checksum(squeezed);

        // then
        assertEquals(6279058075753L, checksum);
    }
}
