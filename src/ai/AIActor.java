/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ai;

import assets.AssetHelper;
import com.bulletphysics.collision.shapes.CapsuleShape;
import com.jme3.bounding.BoundingVolume;
import com.jme3.bullet.collision.shapes.CapsuleCollisionShape;
import com.jme3.bullet.nodes.PhysicsCharacterNode;
import com.jme3.math.Vector3f;
import com.jme3.scene.Spatial;
import game.Game;
import util.DebugRenderer;

/**
 *
 * @author sshake
 */
public class AIActor
{
    Spatial body;
    PhysicsCharacterNode physicsController;
    String id;

    public AIActor(String guid)
    {
        id = guid;
        body = AssetHelper.createActorModel(false);

        //init physics
        CapsuleCollisionShape shape = new CapsuleCollisionShape(0.3f, 0.9f, 1);
        //CylinderCollisionShape shape = new CylinderCollisionShape(new Vector3f(0.3f, 0.75f, 0.3f), 1);
        physicsController = new PhysicsCharacterNode(shape, 0.4f);
        physicsController.attachDebugShape(Game.getInstance().getAssetManager());
        physicsController.setJumpSpeed(20);
        physicsController.setFallSpeed(1f);
        physicsController.setGravity(1f);
        physicsController.attachChild(body);
        physicsController.setLocalTranslation(new Vector3f(0, 0, 0));
        Game.getInstance().getBulletAppState()
                .getPhysicsSpace().add(physicsController);
        
    }

    public void update(float tpf)
    {

        float x = (float)Math.random()*3 - 1.5f;
        float z = (float)Math.random()*3 - 1.5f;
        Vector3f dir = new Vector3f(x, 0, z).mult(tpf);
        physicsController.setWalkDirection(dir);
    }

    public Spatial getNode()
    {
        return physicsController;
    }

    void setLocalTranslation(Vector3f pos)
    {
        physicsController.setLocalTranslation(pos);
    }
}
