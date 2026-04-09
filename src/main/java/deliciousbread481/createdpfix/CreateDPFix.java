package deliciousbread481.createdpfix;  
  
import net.minecraftforge.fml.common.Mod;  
import org.apache.logging.log4j.LogManager;  
import org.apache.logging.log4j.Logger;  
  
@Mod("createdpfix")  
public class CreateDPFix {  
    public static final Logger LOGGER = LogManager.getLogger("CreateDPFix");  
  
    public CreateDPFix() {  
        LOGGER.info("Create DirectionProperty Fix loaded - EncasedFanBlockEntity FACING crash fix active.");  
    }  
}