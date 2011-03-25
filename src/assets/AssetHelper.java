/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package assets;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Cylinder;
import game.Game;

/**
 *
 * @author sshake
 */
public class AssetHelper {
    static AssetManager assetMgr;

    static
    {
        assetMgr = Game.getInstance().getAssetManager();
    }

    public static Material getLitMaterial(ColorRGBA color)
    {
        ColorRGBA diffuse = color;
        ColorRGBA ambient = color.mult(0.2f);
        ColorRGBA specular = diffuse.add(new ColorRGBA(0.4f, 0.4f, 0.4f, 0f));
        Material mat_lit = new Material(assetMgr, "Common/MatDefs/Light/Lighting.j3md");
        mat_lit.setColor("m_Ambient", ambient);
        mat_lit.setColor("m_Diffuse", diffuse);
        mat_lit.setColor("m_Specular", specular);
        mat_lit.setBoolean("m_UseMaterialColors", true);
        mat_lit.setFloat("m_Shininess", 5f); // [0,128]
        return mat_lit;
    }
    public static Geometry createBox(Vector3f pos, Vector3f ext, ColorRGBA color)
    {
        Box b = new Box(pos, ext.x, ext.y, ext.z);
        Geometry geom = new Geometry("Box", b);
        Material mat = getLitMaterial(color);
        geom.setMaterial(mat);
        return geom;
    }

    public static Spatial createActorModel(boolean friendly)
    {
        ColorRGBA ambient, diffuse, specular;
        if(friendly)
        {
            ambient = new ColorRGBA(0.0f, 0.2f, 0.0f, 0f);
            diffuse = ColorRGBA.Green;
            specular = diffuse.add(new ColorRGBA(0.4f, 0.4f, 0.4f, 0f));
        }
        else
        {
            ambient = new ColorRGBA(0.2f, 0f, 0f, 0f);
            diffuse = ColorRGBA.Red;
            specular = diffuse.add(new ColorRGBA(0.4f, 0.4f, 0.4f, 0f));
        }
        Node actor = new Node("Actor");
        Cylinder c = new Cylinder(5, 20, 0.3f, 1.5f, true);
        Geometry body = new Geometry("Body", c);

        body.setMaterial(getLitMaterial(diffuse));
        float rot[] = {(float)Math.PI/2.0f, 0.0f, 0.0f};
        body.setLocalRotation(new Quaternion(rot));
        actor.attachChild(body);
        return actor;
    }


}
