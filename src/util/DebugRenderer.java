package util;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.debug.Arrow;
import com.jme3.scene.shape.Line;
import game.Game;
import java.util.HashMap;
import java.util.LinkedList;

/**
 *
 * @author sshake
 */
public class DebugRenderer {

    private static final float POINT_LENGTH = 2f;
    private static DebugRenderer instance;
    private HashMap<Integer, Spatial> objects;
    private LinkedList<Spatial> tempObjects;
    private Node debugNode;
    private int i;

    private DebugRenderer() 
    {i=0;
        objects = new HashMap<Integer, Spatial>();
        tempObjects = new LinkedList<Spatial>();
        debugNode = new Node("Debug node");
        Game.getInstance().getRootNode().attachChild(debugNode);
    }

    public static DebugRenderer getInstance()
    {
        if(instance == null)
            instance = new DebugRenderer();
        return instance;
    }

    public void update()
    {
        for(Spatial object : tempObjects)
            debugNode.detachChild(object);

        tempObjects.clear();
    }
    
    public void point(int id, Vector3f pos)
    {
        Spatial point = objects.get(id);
        if(point != null)
        {
            point.setLocalTranslation(pos);
        }
        else
        {
            Node n = new Node("Debug point id " + String.valueOf(id));
            Geometry x = new Geometry("x", new Line(Vector3f.ZERO.subtract(POINT_LENGTH, 0, 0), Vector3f.ZERO.add(POINT_LENGTH, 0, 0)));
            Geometry y = new Geometry("y", new Line(Vector3f.ZERO.subtract(0, POINT_LENGTH, 0), Vector3f.ZERO.add(0, POINT_LENGTH, 0)));
            Geometry z = new Geometry("z", new Line(Vector3f.ZERO.subtract(0, 0, POINT_LENGTH), Vector3f.ZERO.add(0, 0, POINT_LENGTH)));
            x.setMaterial(getDebugMaterial());
            y.setMaterial(getDebugMaterial());
            z.setMaterial(getDebugMaterial());
            n.attachChild(x);
            n.attachChild(y);
            n.attachChild(z);
            n.setLocalTranslation(pos);
            debugNode.attachChild(n);
            objects.put(id, n);
        }
    }

    public void line(int id, Vector3f start, Vector3f end)
    {
        if(objects.get(id) != null)
        {
            objects.remove(id);
        }

        Spatial line = createDebugLine(start, end, ColorRGBA.White);
        debugNode.attachChild(line);
        objects.put(id, line);
    }

    public void lineOnce(Vector3f start, Vector3f end, ColorRGBA color)
    {

       /* Spatial line = createDebugLine(start, end, color);
        debugNode.attachChild(line);
        debugNode.updateGeometricState();
        tempObjects.add(line);*/

        ++i;
    }

    public void arrows(int id, Vector3f pos)
    {
        Spatial arrows = objects.get(id);
        if(arrows != null)
        {
            arrows.setLocalTranslation(pos);
        }
        else
        {
            Node node = new Node("Debug arrow id " + id);

            Arrow xa = new Arrow(Vector3f.UNIT_X);
            xa.setLineWidth(4); // make arrow thicker
            Geometry x = new Geometry("arrow x", xa);
            x.setMaterial(getDebugMaterial(ColorRGBA.Red));
            node.attachChild(x);

            Arrow ya = new Arrow(Vector3f.UNIT_Y);
            ya.setLineWidth(4); // make arrow thicker
            Geometry y = new Geometry("arrow y", ya);
            y.setMaterial(getDebugMaterial(ColorRGBA.Green));
            node.attachChild(y);

            Arrow za = new Arrow(Vector3f.UNIT_Z);
            za.setLineWidth(4); // make arrow thicker
            Geometry z = new Geometry("arrow z", za);
            z.setMaterial(getDebugMaterial(ColorRGBA.Blue));
            node.attachChild(z);

            debugNode.attachChild(node);
            objects.put(id, node);
        }
    }

    public Material getDebugMaterial(ColorRGBA color)
    {
        AssetManager assetMgr = Game.getInstance().getAssetManager();
        Material mat = new Material(assetMgr, "Common/MatDefs/Misc/SolidColor.j3md");
        mat.getAdditionalRenderState().setWireframe(true);
        mat.setColor("m_Color", color);
        return mat;

    }

    public Material getDebugMaterial()
    {
        return getDebugMaterial(ColorRGBA.White);
    }

    private Spatial createDebugLine(Vector3f start, Vector3f end, ColorRGBA color)
    {
        Geometry line = new Geometry("Debug Line", new Line(Vector3f.ZERO, end.subtract(start)));
        line.setMaterial(getDebugMaterial(color));
        line.setLocalTranslation(start);
        return line;
    }

}
