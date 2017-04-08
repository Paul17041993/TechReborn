package teamreborn.techreborn.reborncore;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

/**
 * Created by Gigabit101 on 21/01/2017.
 */
//TODO move to RebornCore
public final class VanillaPacketDispatcher
{
    public static void dispatchTEToNearbyPlayers(TileEntity tile)
    {
        if (tile.getWorld() instanceof WorldServer)
        {
            WorldServer ws = ((WorldServer) tile.getWorld());
            SPacketUpdateTileEntity packet = tile.getUpdatePacket();

            if (packet == null)
                return;

            for (EntityPlayer player : ws.playerEntities)
            {
                EntityPlayerMP playerMP = ((EntityPlayerMP) player);

                if (playerMP.getDistanceSq(tile.getPos()) < 64 * 64 && ws.getPlayerChunkMap().isPlayerWatchingChunk(playerMP, tile.getPos().getX() >> 4, tile.getPos().getZ() >> 4))
                {
                    playerMP.connection.sendPacket(packet);
                }
            }
        }
    }

    public static void dispatchTEToNearbyPlayers(World world, BlockPos pos)
    {
        TileEntity tile = world.getTileEntity(pos);
        if (tile != null)
            dispatchTEToNearbyPlayers(tile);
    }

    public static float pointDistancePlane(double x1, double y1, double x2, double y2)
    {
        return (float) Math.hypot(x1 - x2, y1 - y2);
    }
}
