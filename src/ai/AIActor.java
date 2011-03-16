/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ai;

import assets.AssetHelper;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

/**
 *
 * @author sshake
 */
public class AIActor
{
    Node node;
    Spatial body;

    public AIActor()
    {
        node = new Node("AIActor node");
        body = AssetHelper.createActorModel(false);
        node.attachChild(body);

    }

    public void update(float tpf)
    {

    }

    public Spatial getNode()
    {
        return node;
    }

    void setLocalTranslation(Vector3f pos)
    {
        node.setLocalTranslation(pos);
    }
}
