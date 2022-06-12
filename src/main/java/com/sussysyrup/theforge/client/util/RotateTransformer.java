package com.sussysyrup.theforge.client.util;

import net.fabricmc.fabric.api.renderer.v1.mesh.MutableQuadView;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3f;

public class RotateTransformer implements RenderContext.QuadTransform {

    private final Direction direction;
    private final float center;

    public RotateTransformer(Direction direction, float center)
    {
        this.direction = direction;
        this.center = center;
    }

    @Override
    public boolean transform(MutableQuadView quad) {

        BakedQuad bakedQuad = quad.toBakedQuad(0, null, false);

        int[] data = bakedQuad.getVertexData();

        for(int index = 0; index < 4; index++)
        {
            int offset = index * 8;
            float x = Float.intBitsToFloat(data[offset]);
            float y = Float.intBitsToFloat(data[offset + 1]);
            float z = Float.intBitsToFloat(data[offset + 2]);

            float saveX = x;
            float saveZ = z;

            if(direction.equals(Direction.EAST))
            {
                saveZ = x;
                saveX = center + (center-z);

                saveX -=center;
            }
            if(direction.equals(Direction.SOUTH))
            {
                x = center + (center-x);
                z = center + (center-z);
                x -=center;
                z -=center;

                saveX = x;
                saveZ = z;
            }
            if(direction.equals(Direction.WEST))
            {
                saveX = z;
                saveZ = center + (center-x);

                saveZ -=center;
            }

            quad.pos(index, new Vec3f(saveX, y ,saveZ));
        }

        return true;
    }
}
