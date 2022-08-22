package p06_TirePressureMonitoringSystem;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

public class AlarmTest {

    @Test
    public void testLowPressureShouldTurnAlarmOn(){
        Sensor sensor = Mockito.mock(Sensor.class);

        Mockito.when(sensor.popNextPressurePsiValue()).thenReturn(16.999);

        Alarm alarm = new Alarm(sensor);

        alarm.check();

        Assert.assertTrue(alarm.getAlarmOn());
    }

    @Test
    public void testHighPressureShouldTurnAlarmOn(){
        Sensor sensor = Mockito.mock(Sensor.class);

        Mockito.when(sensor.popNextPressurePsiValue()).thenReturn(21.001);

        Alarm alarm = new Alarm(sensor);

        alarm.check();

        Assert.assertTrue(alarm.getAlarmOn());
    }

    @Test
    public void testNormalPressureShouldNotTurnAlarmOn(){
        Sensor sensor = Mockito.mock(Sensor.class);

        Mockito.when(sensor.popNextPressurePsiValue()).thenReturn(18D);

        Alarm alarm = new Alarm(sensor);

        alarm.check();

        Assert.assertFalse(alarm.getAlarmOn());
    }

}