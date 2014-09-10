package minefantasy.system.network;

import com.google.common.io.ByteArrayDataInput;

public interface PacketUserMF {
	public void recievePacket(ByteArrayDataInput data);
}
