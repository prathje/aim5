/*
Copyright (c) 2011 Tsz-Chiu Au, Peter Stone
University of Texas at Austin
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:

1. Redistributions of source code must retain the above copyright notice, this
list of conditions and the following disclaimer.

2. Redistributions in binary form must reproduce the above copyright notice,
this list of conditions and the following disclaimer in the documentation
and/or other materials provided with the distribution.

3. Neither the name of the University of Texas at Austin nor the names of its
contributors may be used to endorse or promote products derived from this
software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package aim4.config;


import java.text.*;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;

/**
 * A class to hold constants for the simulator.
 */
public final class Constants {

  /**
   * A NumberFormat for zero places after the decimal.  Used a lot, so might as
   * well just define it once.
   */
  public static final NumberFormat ZERO_DEC = new DecimalFormat("#");

  /**
   * A NumberFormat for one place after the decimal.  Used a lot, so might as
   * well just define it once.
   */
  public static final NumberFormat ONE_DEC = new DecimalFormat("#.0");

  /**
   * A NumberFormat for two places after the decimal.  Used a lot, so might as
   * well just define it once.
   */
  public static final NumberFormat TWO_DEC = new DecimalFormat("#.00");

  /**
   * A NumberFormat for ten places after the decimal.  Used a lot, so might as
   * well just define it once.
   */
  public static final NumberFormat TEN_DEC = new DecimalFormat("#.0000000000");

  /** The number of second per hour */
  public static final int numOfSecondPerHour = 3600;

  /**
   * A NumberFormat that ensures a width of at least 8 with leading zeroes.
   */
  public static final NumberFormat LEADING_ZEROES =
    new DecimalFormat("00000000");

  /**
   * The size, in bits, of an integer on our theoretical platform.
   * {@value} bits.
   */
  public static final int INTEGER_SIZE = 32; // 32 bits

  /**
   * The size, in bits, of a double-precision floating point number on our
   * theoretical platform. {@value} bits.
   */
  public static final int DOUBLE_SIZE = 64; // 64 bits

  /**
   * The size, in bits, of a boolean value on our theoretical platform.
   * {@value} bits.
   */
  public static final int BOOLEAN_SIZE = 1; // 1 bit

  /**
   * The size, in bits, of an enumerated data type on our theoretical
   * platform. {@value} bits.
   */
  public static final int ENUM_SIZE = 8; // 8 bits

  /** The number of bits per byte ({@value}). */
  public static final int BITS_PER_BYTE = 8;

  /** The number of bytes per kilobyte ({@value}). */
  public static final int BYTES_PER_KB = 1024;

  /** The number of bits per kilobyte ({@value}). */
  public static final int BITS_PER_KB = BYTES_PER_KB * BITS_PER_BYTE;

  /** The number of bytes per megabyte ({@value}). */
  public static final int BYTES_PER_MB = BYTES_PER_KB * 1024;

  /**
   * The precision with which two double values are considered equal.
   * The equality of two double values a and b should be tested by
   * using Math.abs(a-b) < Constants.DOUBLE_EQUAL_PRECISION. {@value}
   */
  public static final double DOUBLE_EQUAL_PRECISION = 0.0000000001;

  /**
   * The precision with which two double values are considered equal.
   * The equality of two double values a and b should be tested by
   * using Math.abs(a-b) < Constants.DOUBLE_EQUAL_PRECISION. {@value}
   */
  public static final double DOUBLE_EQUAL_WEAK_PRECISION = 0.000001;

  /** The time buffer for edge tiles according to velocity ranges
   *  The key is a pair of velocity ranges and the value is the edge tile time buffer size */
  private static final Map<AbstractMap.SimpleEntry<Double, Double>, Double> MAP_VELOCITY_TO_EDGE_TILE_TIME_BUFFER = mapVelocityToEdgeTileTimeBuffer();

  private static final Map<AbstractMap.SimpleEntry<Double,Double>,Double> mapVelocityToEdgeTileTimeBuffer() {
    Map<AbstractMap.SimpleEntry<Double, Double>, Double> mapVelocityToEdgeTileTimeBuffer = new HashMap<AbstractMap.SimpleEntry<Double, Double>, Double>();

    mapVelocityToEdgeTileTimeBuffer.put(new AbstractMap.SimpleEntry<>(0.0, 15.0), 0.3);
    mapVelocityToEdgeTileTimeBuffer.put(new AbstractMap.SimpleEntry<>(15.0, 30.0), 0.5);
    mapVelocityToEdgeTileTimeBuffer.put(new AbstractMap.SimpleEntry<>(30.0, 45.0), 0.7);
    mapVelocityToEdgeTileTimeBuffer.put(new AbstractMap.SimpleEntry<>(45.0, 55.0), 0.9);
    mapVelocityToEdgeTileTimeBuffer.put(new AbstractMap.SimpleEntry<>(55.0, 65.0), 1.1);
    mapVelocityToEdgeTileTimeBuffer.put(new AbstractMap.SimpleEntry<>(65.0, 75.0), 1.3);
    mapVelocityToEdgeTileTimeBuffer.put(new AbstractMap.SimpleEntry<>(75.0, 80.0), 1.5);

    return  mapVelocityToEdgeTileTimeBuffer;

  }

  /** Retrieve the edge tile buffer size based on a given velocity.
   *  Throws a runtime exception if no edge tile time buffer can be found. */
  public static double getEdgeTileTimeBufferBasedOnVelocity(double velocity) throws Exception {
    for (AbstractMap.SimpleEntry<Double, Double> velocityRanges : MAP_VELOCITY_TO_EDGE_TILE_TIME_BUFFER.keySet()) {
      if (velocityRanges.getKey().doubleValue() <= velocity && velocity <= velocityRanges.getValue().doubleValue()) {
        return MAP_VELOCITY_TO_EDGE_TILE_TIME_BUFFER.get(velocityRanges).doubleValue();
      }
    }
    throw new RuntimeException(String.format("No edge tile time buffer size value could be found for the given velocity of %f.", velocity));
  }

  /** The minimum following distance according to velocity ranges
   *  The key is a pair of velocity ranges and the value is the minimum following distance */
  private static final Map<AbstractMap.SimpleEntry<Double, Double>, Double> MAP_VELOCITY_TO_MINIMUM_FOLLOWING_DISTANCES = mapVelocityToMinimumFollowingDistances();

  private static Map<AbstractMap.SimpleEntry<Double,Double>,Double> mapVelocityToMinimumFollowingDistances() {
    Map<AbstractMap.SimpleEntry<Double, Double>, Double> mapVelocityToMinimumFollowingDistances = new HashMap<AbstractMap.SimpleEntry<Double, Double>, Double>();

    mapVelocityToMinimumFollowingDistances.put(new AbstractMap.SimpleEntry<>(0.0, 15.0), 0.5);
    mapVelocityToMinimumFollowingDistances.put(new AbstractMap.SimpleEntry<>(15.0, 30.0), 0.6);
    mapVelocityToMinimumFollowingDistances.put(new AbstractMap.SimpleEntry<>(30.0, 45.0), 0.9);
    mapVelocityToMinimumFollowingDistances.put(new AbstractMap.SimpleEntry<>(45.0, 55.0), 1.1);
    mapVelocityToMinimumFollowingDistances.put(new AbstractMap.SimpleEntry<>(55.0, 65.0), 1.2);
    mapVelocityToMinimumFollowingDistances.put(new AbstractMap.SimpleEntry<>(65.0, 75.0), 1.3);
    mapVelocityToMinimumFollowingDistances.put(new AbstractMap.SimpleEntry<>(75.0, 80.0), 1.5);

    return  mapVelocityToMinimumFollowingDistances;

  }

  /** Retrieve the minimum following distance based on a given velocity.
   *  Returns Double.MAX_VALUE */
  public static double getMinimumFollowingDistanceBasedOnVelocity(double velocity) throws Exception {
    for (AbstractMap.SimpleEntry<Double, Double> velocityRanges : MAP_VELOCITY_TO_MINIMUM_FOLLOWING_DISTANCES.keySet()) {
      if (velocityRanges.getKey().doubleValue() <= velocity && velocity <= velocityRanges.getValue().doubleValue()) {
        return MAP_VELOCITY_TO_MINIMUM_FOLLOWING_DISTANCES.get(velocityRanges).doubleValue();
      }
    }
    throw new RuntimeException(String.format("No value for minimum following distances could be found for the given velocity of %f.", velocity));
  }

  /**
   * The four cardinal directions: north, east, south, and west.
   */
  public enum CardinalDirection {
    /** North */
    NORTH,
    /** East */
    EAST,
    /** South */
    SOUTH,
    /** West */
    WEST
  };

  /**
   * A direction to turn at an intersection.
   */
  public enum TurnDirection {
    /**
     * Left turn.
     */
    LEFT,
    /**
     * Right turn.
     */
    RIGHT,
    /**
     * Go straight (i.e. no turn).
     */
    STRAIGHT,
    /**
     * 180 degree turn back to the same road (currently not allowed).
     */
    U_TURN,
  }

  /**
   * The three possible states of a traffic light: green, yellow, and red.
   */
  public enum LightStatus {
    /** A green light means go. */
    GREEN,
    /** Yellow light means a red light is coming soon. */
    YELLOW,
    /** Red means stop. */
    RED
  };

}
