package club.sk1er.patcher.hooks;

import club.sk1er.patcher.Patcher;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.ServerList;
import org.apache.logging.log4j.Logger;

public class ServerListHook {

    private static final Logger logger = Patcher.instance.getLogger();

    public static ServerData getServerData(ServerList list, int index) {
        try {
            return list.servers.get(index);
        } catch (Exception e) {
            logger.error("Failed to get server data.", e);
        }

        return null;
    }

    public static void removeServerData(ServerList list, int index) {
        try {
            list.servers.remove(index);
        } catch (Exception e) {
            logger.error("Failed to remove server data.", e);
        }
    }

    public static void addServerData(ServerList list, ServerData index) {
        try {
            list.servers.add(index);
        } catch (Exception e) {
            logger.error("Failed to add server data.", e);
        }
    }

    public static void swapServers(ServerList list, int pos1, int pos2) {
        try {
            ServerData serverData = list.getServerData(pos1);
            list.servers.set(pos1, list.getServerData(pos2));
            list.servers.set(pos2, serverData);
            list.saveServerList();
        } catch (Exception e) {
            logger.error("Failed to swap servers.", e);
        }
    }

    public static void set(ServerList list, int index, ServerData sever) {
        try {
            list.servers.set(index, sever);
        } catch (Exception e) {
            logger.error("Failed to set server data.", e);
        }
    }
}