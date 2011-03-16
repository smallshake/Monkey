package game;

import ai.AIActor;
import ai.AIManager;
import assets.AssetHelper;
import com.jme3.app.SimpleApplication;
import com.jme3.input.FlyByCamera;
import com.jme3.light.DirectionalLight;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Spatial;
import level.Level;
import util.DebugRenderer;

/**
 * test
 * @author sshake
 */
public class Game extends SimpleApplication {

    private Spatial player;
    private AIActor enemy;

    private static Game instance;

    public static void main(String[] aargs) {
        Game app = getInstance();
        app.start();
    }

    public static Game getInstance()
    {
        if(instance == null)
            instance = new Game();
        return instance;
    }

    private Game()
    {
    }

    @Override
    public void simpleInitApp()
    {
        cam.setLocation(new Vector3f(-7f, 11f, 8f));
        cam.lookAt(new Vector3f(0f, 0f, -1f), Vector3f.UNIT_Y);
        FlyByCamera flycam = getFlyByCamera();
        flycam.setMoveSpeed(10f);

        Level lvl = new Level(this);
        lvl.init();

        player = AssetHelper.createActorModel(true);
        rootNode.attachChild(player);

        AIManager aiMgr = lvl.getAIManager();
        aiMgr.createEnemy(new Vector3f(3.0f, 0.0f, 1.0f));
        aiMgr.createEnemy(new Vector3f(-4.0f, 0.0f, 0.5f));
        aiMgr.createEnemy(new Vector3f(2.0f, 2.0f, -5.0f));



        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(-0.3f, -0.8f, -1.0f));
        rootNode.addLight(sun);
        DebugRenderer.getInstance().point("Test".hashCode(), Vector3f.UNIT_XYZ);
        DebugRenderer.getInstance().point("Test2".hashCode(), Vector3f.UNIT_X);
        DebugRenderer.getInstance().line("TestLine".hashCode(), Vector3f.UNIT_XYZ, Vector3f.UNIT_X);
        DebugRenderer.getInstance().arrows("TestArrows".hashCode(), Vector3f.UNIT_XYZ);
    }

    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }

    
}
