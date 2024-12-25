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

package ua.nanit.limbo.world;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import net.kyori.adventure.nbt.CompoundBinaryTag;
import net.kyori.adventure.nbt.ListBinaryTag;
import net.kyori.adventure.nbt.TagStringIO;
import ua.nanit.limbo.server.Log;

public final class DimensionRegistry {

    private final ClassLoader classLoader;

    private Dimension defaultDimension_1_16;
    private Dimension defaultDimension_1_18_2;
    private Dimension dimension_1_20_5;
    private Dimension dimension_1_21;
    private Dimension dimension_1_21_2;
    private Dimension dimension_1_21_4;

    private CompoundBinaryTag codec_1_16;
    private CompoundBinaryTag codec_1_18_2;
    private CompoundBinaryTag codec_1_19;
    private CompoundBinaryTag codec_1_19_1;
    private CompoundBinaryTag codec_1_19_4;
    private CompoundBinaryTag codec_1_20;
    private CompoundBinaryTag codec_1_20_5;
    private CompoundBinaryTag codec_1_21;
    private CompoundBinaryTag codec_1_21_2;
    private CompoundBinaryTag codec_1_21_4;
    private CompoundBinaryTag oldCodec;

    private CompoundBinaryTag tags_1_20_5;

    public DimensionRegistry(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public CompoundBinaryTag getCodec_1_16() {
        return codec_1_16;
    }

    public CompoundBinaryTag getCodec_1_18_2() {
        return codec_1_18_2;
    }

    public CompoundBinaryTag getCodec_1_19() {
        return codec_1_19;
    }

    public CompoundBinaryTag getCodec_1_19_1() {
        return codec_1_19_1;
    }

    public CompoundBinaryTag getCodec_1_19_4() {
        return codec_1_19_4;
    }

    public CompoundBinaryTag getCodec_1_20() {
        return codec_1_20;
    }

    public CompoundBinaryTag getCodec_1_20_5() {
        return codec_1_20_5;
    }

    public CompoundBinaryTag getCodec_1_21() {
        return codec_1_21;
    }

    public CompoundBinaryTag getCodec_1_21_2() {
        return codec_1_21_2;
    }

    public CompoundBinaryTag getCodec_1_21_4() {
        return codec_1_21_4;
    }

    public CompoundBinaryTag getOldCodec() {
        return oldCodec;
    }

    public Dimension getDefaultDimension_1_16() {
        return defaultDimension_1_16;
    }

    public Dimension getDefaultDimension_1_18_2() {
        return defaultDimension_1_18_2;
    }

    public Dimension getDimension_1_20_5() {
        return dimension_1_20_5;
    }

    public Dimension getDimension_1_21() {
        return dimension_1_21;
    }

    public Dimension getDimension_1_21_2() {
        return dimension_1_21_2;
    }

    public Dimension getDimension_1_21_4() {
        return dimension_1_21_4;
    }

    public CompoundBinaryTag getTags_1_20_5() {
        return tags_1_20_5;
    }

    public void load(String def) throws IOException {
        // On 1.16-1.16.1 different codec format
        oldCodec = readSnbtFile("/dimension/codec_old.snbt");
        codec_1_16 = readSnbtFile("/dimension/codec_1_16.snbt");
        codec_1_18_2 = readSnbtFile("/dimension/codec_1_18_2.snbt");
        codec_1_19 = readSnbtFile("/dimension/codec_1_19.snbt");
        codec_1_19_1 = readSnbtFile("/dimension/codec_1_19_1.snbt");
        codec_1_19_4 = readSnbtFile("/dimension/codec_1_19_4.snbt");
        codec_1_20 = readSnbtFile("/dimension/codec_1_20.snbt");
        codec_1_20_5 = readSnbtFile("/dimension/codec_1_20_5.snbt");
        codec_1_21 = readSnbtFile("/dimension/codec_1_21.snbt");
        codec_1_21_2 = readSnbtFile("/dimension/codec_1_21_2.snbt");
        codec_1_21_4 = readSnbtFile("/dimension/codec_1_21_4.snbt");

        tags_1_20_5 = readSnbtFile("/dimension/tags_1_20_5.snbt");

        defaultDimension_1_16 = getDefaultDimension(def, codec_1_16);
        defaultDimension_1_18_2 = getDefaultDimension(def, codec_1_18_2);

        dimension_1_20_5 = getModernDimension(def, codec_1_20_5);
        dimension_1_21 = getModernDimension(def, codec_1_21);
        dimension_1_21_2 = getModernDimension(def, codec_1_21_2);
        dimension_1_21_4 = getModernDimension(def, codec_1_21_4);
    }

    private Dimension getDefaultDimension(String def, CompoundBinaryTag tag) {
        ListBinaryTag dimensions = tag.getCompound("minecraft:dimension_type").getList("value");

        switch (def) {
            case "minecraft:overworld": {
                CompoundBinaryTag overWorld = (CompoundBinaryTag) ((CompoundBinaryTag) dimensions.get(0)).get("element");
                return new Dimension(0, def, overWorld);
            }
            case "minecraft:the_nether": {
                CompoundBinaryTag theNether = (CompoundBinaryTag) ((CompoundBinaryTag) dimensions.get(2)).get("element");
                return new Dimension(-1, def, theNether);
            }
            case "minecraft:the_end": {
                CompoundBinaryTag theEnd = (CompoundBinaryTag) ((CompoundBinaryTag) dimensions.get(3)).get("element");
                return new Dimension(1, def, theEnd);
            }
            default: {
                CompoundBinaryTag theEnd = (CompoundBinaryTag) ((CompoundBinaryTag) dimensions.get(3)).get("element");
                Log.warning("Undefined dimension type: '%s'. Using OVERWORLD as default", def);
                return new Dimension(0, "minecraft:overworld", theEnd);
            }
        }
    }

    private Dimension getModernDimension(String def, CompoundBinaryTag tag) {
        ListBinaryTag dimensions = tag.getCompound("minecraft:dimension_type").getList("value");

        for (int i = 0; i < dimensions.size(); i++) {
            CompoundBinaryTag dimension = (CompoundBinaryTag) dimensions.get(i);

            String name = dimension.getString("name");
            CompoundBinaryTag world = (CompoundBinaryTag) dimension.get("element");

            if (name.startsWith(def)) {
                return new Dimension(i, name, world);
            }
        }

        CompoundBinaryTag overWorld = (CompoundBinaryTag) ((CompoundBinaryTag) dimensions.get(0)).get("element");
        Log.warning("Undefined dimension type: '%s'. Using OVERWORLD as default", def);
        return new Dimension(0, "minecraft:overworld", overWorld);
    }

    private CompoundBinaryTag readSnbtFile(String resPath) throws IOException {
        String originalPath = resPath.startsWith("/") ? resPath.substring(1) : resPath;
        InputStream in = classLoader.getResourceAsStream(originalPath);

        if (in == null) {
            throw new FileNotFoundException("Cannot find snbt file " + resPath);
        }

        return TagStringIO.get().asCompound(streamToString(in));
    }

    private String streamToString(InputStream in) throws IOException {
        try (BufferedReader bufReader = new BufferedReader(new InputStreamReader(in, StandardCharsets.UTF_8))) {
            return bufReader.lines().collect(Collectors.joining("\n"));
        }
    }
}