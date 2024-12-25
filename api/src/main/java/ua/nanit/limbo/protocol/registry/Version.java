/*
 * Copyright (C) 2020 Nan1t
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package ua.nanit.limbo.protocol.registry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum Version {

    UNDEFINED(-1, List.of("UNDEFINED")),
    V1_7_2(4, List.of("1.7.2", "1.7.3", "1.7.4", "1.7.5")),
    // 1.7.2-1.7.5 has same protocol numbers
    V1_7_6(5, List.of("1.7.6", "1.7.7", "1.7.8", "1.7.9", "1.7.10")),
    // 1.7.6-1.7.10 has same protocol numbers
    V1_8(47, List.of("1.8", "1.8.1", "1.8.2", "1.8.3", "1.8.4", "1.8.5", "1.8.6", "1.8.7", "1.8.8")),
    // 1.8-1.8.8 has same protocol numbers
    V1_9(107, List.of("1.9")),
    V1_9_1(108, List.of("1.9.1")),
    V1_9_2(109, List.of("1.9.2")),
    V1_9_4(110, List.of("1.9.4")),
    V1_10(210, List.of("1.10", "1.10.1", "1.10.2")),
    // 1.10-1.10.2 has same protocol numbers
    V1_11(315, List.of("1.11")),
    V1_11_1(316, List.of("1.11.1", "1.11.2")),
    // 1.11.2 has same protocol number
    V1_12(335, List.of("1.12")),
    V1_12_1(338, List.of("1.12.1")),
    V1_12_2(340, List.of("1.12.2")),
    V1_13(393, List.of("1.13")),
    V1_13_1(401, List.of("1.13.1")),
    V1_13_2(404, List.of("1.13.2")),
    V1_14(477, List.of("List.of(1.14")),
    V1_14_1(480, List.of("1.14.1")),
    V1_14_2(485, List.of("1.14.2")),
    V1_14_3(490, List.of("1.14.3")),
    V1_14_4(498, List.of("1.14.4")),
    V1_15(573, List.of("1.15")),
    V1_15_1(575, List.of("1.15.1")),
    V1_15_2(578, List.of("1.15.2")),
    V1_16(735, List.of("1.16")),
    V1_16_1(736, List.of("1.16.1")),
    V1_16_2(751, List.of("1.16.2")),
    V1_16_3(753, List.of("1.16.3")),
    V1_16_4(754, List.of("1.16.4", "1.16.5")),
    // 1.16.5 has same protocol number
    V1_17(755, List.of("1.17")),
    V1_17_1(756, List.of("1.17.1")),
    V1_18(757, List.of("1.18", "1.18.1")),
    // 1.18.1 has same protocol number
    V1_18_2(758, List.of("1.18.2")),
    V1_19(759, List.of("1.19")),
    V1_19_1(760, List.of("1.19.1", "1.19.2")),
    // 1.19.2 has same protocol number
    V1_19_3(761, List.of("1.19.3")),
    V1_19_4(762, List.of("1.19.4")),
    V1_20(763, List.of("1.20", "1.20.1")),
    // 1.20.1 has same protocol number
    V1_20_2(764, List.of("1.20.2")),
    V1_20_3(765, List.of("1.20.3")),
    V1_20_5(766, List.of("1.20.5", "1.20.6")),
    // 1.20.6 has same protocol number
    V1_21(767, List.of("1.21", "1.21.1")),
    // 1.21.1 has same protocol number
    V1_21_2(768, List.of("1.21.2", "1.21.3")),
    // 1.21.3 has same protocol number
    V1_21_4(769, List.of("1.21.4"));

    private static final Map<Integer, Version> VERSION_MAP;
    private static final Version MAX;

    static {
        Version[] values = values();

        VERSION_MAP = new HashMap<>();
        MAX = values[values.length - 1];

        Version last = null;
        for (Version version : values) {
            version.prev = last;
            last = version;
            VERSION_MAP.put(version.getProtocolNumber(), version);
        }
    }

    private final int protocolNumber;
    private final List<String> displayNames;
    private Version prev;

    Version(int protocolNumber, List<String> displayNames) {
        this.protocolNumber = protocolNumber;
        this.displayNames = displayNames;
    }

    public int getProtocolNumber() {
        return this.protocolNumber;
    }

    public List<String> getDisplayNames() {
        return displayNames;
    }

    public Version getPrev() {
        return prev;
    }

    public boolean more(Version another) {
        return this.protocolNumber > another.protocolNumber;
    }

    public boolean moreOrEqual(Version another) {
        return this.protocolNumber >= another.protocolNumber;
    }

    public boolean less(Version another) {
        return this.protocolNumber < another.protocolNumber;
    }

    public boolean lessOrEqual(Version another) {
        return this.protocolNumber <= another.protocolNumber;
    }

    public boolean fromTo(Version min, Version max) {
        return this.protocolNumber >= min.protocolNumber && this.protocolNumber <= max.protocolNumber;
    }

    public boolean isSupported() {
        return this != UNDEFINED;
    }

    public static Version getMin() {
        return V1_7_2;
    }

    public static Version getMax() {
        return MAX;
    }

    public static Version of(int protocolNumber) {
        return VERSION_MAP.getOrDefault(protocolNumber, UNDEFINED);
    }
}
