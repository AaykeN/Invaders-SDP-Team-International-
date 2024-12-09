package level_design;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mockito;
import engine.Frame;
import java.io.InputStream;

// Remember to put background variables to public for the test class to use them

/*class BackgroundTest {

    private Background background;
    private Frame mockFrame;

    @BeforeEach
    void setUp() {
        // Initialize the singleton instance
        background = Background.getInstance();

        // Mock the Frame object to simulate screen width and height
        mockFrame = Mockito.mock(engine.Frame.class);
        Mockito.when(mockFrame.getWidth()).thenReturn(800);
        Mockito.when(mockFrame.getHeight()).thenReturn(600);
    }

    @Test
    void testSingletonInstance() {
        Background anotherInstance = Background.getInstance();
        assertSame(background, anotherInstance, "Instances should be the same (singleton pattern).");
    }

    @Test
    void testInitialize() {
        background.initialize((engine.Frame) mockFrame);
        // Assert that the background's screen width and height were correctly set
        assertEquals(800, background.screenWidth, "Screen width should be set correctly.");
        assertEquals(600, background.screenHeight, "Screen height should be set correctly.");
    }

    @Test
    void testBackgroundReset() {
        background.initialize(mockFrame);
        background.backgroundReset(1200, 1600);

        // Verify that the background's properties are correctly reset
        assertEquals(1600, background.backgroundWidth, "Background width should be reset.");
        assertEquals(1200, background.backgroundHeight, "Background height should be reset.");
        assertEquals(-800, background.horizontalOffset, "Horizontal offset should be set correctly.");
        assertEquals(0, background.verticalOffset, "Vertical offset should be set correctly.");
        assertEquals(3, background.scrollSpeedHorizontal, "Horizontal scroll speed should be set correctly.");
        assertEquals(1, background.scrollSpeedVertical, "Vertical scroll speed should be set correctly.");
        assertEquals(0, background.offsetUpdateInterval, "Offset update interval should be reset.");
    }

    @Test
    void testGetBackgroundImageStream_validIndex() {
        InputStream stream = Background.getBackgroundImageStream(1);
        assertNotNull(stream, "InputStream should not be null for valid level index.");
    }

    @Test
    void testGetVerticalOffset() {
        background.initialize(mockFrame);
        background.backgroundReset(1200, 1600);

        // Test the vertical offset before any updates
        int initialVerticalOffset = background.getVerticalOffset();
        assertEquals(-1, initialVerticalOffset, "Initial vertical offset should be -1.");

        // Simulate a few updates to vertical offset
        background.getVerticalOffset(); // 1st call increments offsetUpdateInterval
        background.getVerticalOffset(); // 2nd call increments offsetUpdateInterval
        int updatedVerticalOffset = background.getVerticalOffset(); // 3rd call updates vertical offset

        assertNotEquals(initialVerticalOffset, updatedVerticalOffset, "Vertical offset should change after update.");
    }

    @Test
    void testGetHorizontalOffset_moveRight() {
        background.initialize(mockFrame);
        background.backgroundReset(1200, 1600);

        // Test the horizontal offset when moving right
        int initialOffset = background.horizontalOffset;
        int updatedOffset = background.getHorizontalOffset(true, false);

        assertEquals(initialOffset - 3, updatedOffset, "Horizontal offset should decrease when moving right.");
    }

    @Test
    void testGetHorizontalOffset_moveLeft() {
        background.initialize(mockFrame);
        background.backgroundReset(1200, 1600);

        // Test the horizontal offset when moving left
        int initialOffset = background.horizontalOffset;
        int updatedOffset = background.getHorizontalOffset(false, true);

        assertEquals(initialOffset + 3, updatedOffset, "Horizontal offset should increase when moving left.");
    }

    @Test
    void testGetHorizontalOffset_noMove() {
        background.initialize(mockFrame);
        background.backgroundReset(1200, 1600);

        // Test the horizontal offset when no movement occurs
        int initialOffset = background.horizontalOffset;
        int updatedOffset = background.getHorizontalOffset(false, false);

        assertEquals(initialOffset, updatedOffset, "Horizontal offset should not change when not moving.");
    }
}*/
