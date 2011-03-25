package ai;

import com.jme3.math.Vector3f;
import java.util.ArrayList;
import level.Level;

/**
 *
 * @author sshake
 */
public class AIManager {

    ArrayList<AIActor> actors;
    public AIManager(Level lvl)
    {
        level = lvl;
        actors = new ArrayList<AIActor>();
    }

    public void update(float tpf)
    {
        for(AIActor actor : actors)
        {
            actor.update(tpf);
        }
    }

    public AIActor createEnemy(String id, Vector3f pos)
    {
        AIActor actor = new AIActor(id);
        actors.add(actor);
        actor.setLocalTranslation(pos);
        level.getRootNode().attachChild(actor.getNode());
        return actor;
    }

    protected Level level;

}
