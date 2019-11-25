import com.devexperts.dxlab.lincheck.LinChecker;
import com.devexperts.dxlab.lincheck.LoggingLevel;
import com.devexperts.dxlab.lincheck.Options;
import com.devexperts.dxlab.lincheck.annotations.Operation;
import com.devexperts.dxlab.lincheck.annotations.Param;
import com.devexperts.dxlab.lincheck.paramgen.IntGen;
import com.devexperts.dxlab.lincheck.strategy.stress.StressCTest;
import com.devexperts.dxlab.lincheck.strategy.stress.StressOptions;
import org.junit.Test;

@Param(name = "value", gen = IntGen.class, conf = "1:5")
@StressCTest
public class SimpleSetTest {
    private MySet<Integer> set = new LockFreeSet<Integer>();

    @Operation
    public Boolean add(@Param(name = "value") int value) {
        return set.add(value);
    }

    @Operation
    public Boolean remove(@Param(name = "value") int value) {
        return set.remove(value);
    }

    @Operation
    public Boolean contains(@Param(name = "value") int value) {
        return set.contains(value);
    }

    @Test
    public void test() {
        Options opts = new StressOptions()
                .iterations(50)
                .threads(3)
                .actorsPerThread(5)
                .logLevel(LoggingLevel.INFO);
        LinChecker.check(SimpleSetTest.class, opts);
    }
}