package minefantasy.system;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.relauncher.*;

@SideOnly(Side.CLIENT)
public abstract class ExtendedReachHelper
{
        private static Minecraft  mc = FMLClientHandler.instance().getClient();

        /**
         * This method will return the entitly or tile the mouse is hovering over up to the distance provided. It is more or less a copy/paste of the default
         * minecraft version.
         * 
         * @return
         */
        public static MovingObjectPosition getMouseOver(float tickPart, float maxDist)
        {
            Minecraft mc = FMLClientHandler.instance().getClient();
            if (mc.renderViewEntity != null)
            {
                if (mc.theWorld != null)
                {
                    mc.pointedEntityLiving = null;
                    double d0 = (double)maxDist;
                    MovingObjectPosition objectMouseOver = mc.renderViewEntity.rayTrace(d0, tickPart);
                    double d1 = d0;
                    Vec3 vec3 = mc.renderViewEntity.getPosition(tickPart);

                    if (objectMouseOver != null)
                    {
                        d1 = objectMouseOver.hitVec.distanceTo(vec3);
                    }

                    Vec3 vec31 = mc.renderViewEntity.getLook(tickPart);
                    Vec3 vec32 = vec3.addVector(vec31.xCoord * d0, vec31.yCoord * d0, vec31.zCoord * d0);
                    Entity pointedEntity = null;
                    float f1 = 1.0F;
                    List list = mc.theWorld.getEntitiesWithinAABBExcludingEntity(mc.renderViewEntity, mc.renderViewEntity.boundingBox.addCoord(vec31.xCoord * d0, vec31.yCoord * d0, vec31.zCoord * d0).expand((double)f1, (double)f1, (double)f1));
                    double d2 = d1;

                    for (int i = 0; i < list.size(); ++i)
                    {
                        Entity entity = (Entity)list.get(i);

                        if (entity.canBeCollidedWith())
                        {
                            float f2 = entity.getCollisionBorderSize();
                            AxisAlignedBB axisalignedbb = entity.boundingBox.expand((double)f2, (double)f2, (double)f2);
                            MovingObjectPosition movingobjectposition = axisalignedbb.calculateIntercept(vec3, vec32);

                            if (axisalignedbb.isVecInside(vec3))
                            {
                                if (0.0D < d2 || d2 == 0.0D)
                                {
                                    pointedEntity = entity;
                                    d2 = 0.0D;
                                }
                            }
                            else if (movingobjectposition != null)
                            {
                                double d3 = vec3.distanceTo(movingobjectposition.hitVec);

                                if (d3 < d2 || d2 == 0.0D)
                                {
                                    pointedEntity = entity;
                                    d2 = d3;
                                }
                            }
                        }
                    }

                    if (pointedEntity != null && (d2 < d1 || objectMouseOver == null))
                    {
                        objectMouseOver = new MovingObjectPosition(pointedEntity);
                    }

                    return objectMouseOver;
                }
            }
            return null;
        }

}