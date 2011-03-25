package game;

import ai.AIActor;
import ai.AIManager;
import assets.AssetHelper;
import com.jme3.app.SimpleApplication;
import com.jme3.asset.TextureKey;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.collision.shapes.BoxCollisionShape;
import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.bullet.nodes.PhysicsNode;
import com.jme3.input.FlyByCamera;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Sphere;
import com.jme3.texture.Texture;
import level.Level;
import util.BulletDebugDrawer;
import util.DebugRenderer;

/**
 * test
 * @author sshake
 */
public class Game extends SimpleApplication {

    private Spatial player;
    private AIActor enemy;
    private BulletAppState bulletAppState;
    private AIManager aiMgr;

    private static Game instance;

    public static void main(String[] aargs) {
        Game app = getInstance();
        app.start();
    }


    ////////////////////////



  private static final Box brick;
  private static final BoxCollisionShape boxCollisionShape;

  /** brick dimensions */
  private static final float brickLength = 0.48f;
  private static final float brickWidth = 0.24f;
  private static final float brickHeight = 0.12f;

  /** Materials */
  Material wall_mat;

  static
  {
    /** initializing the brick geometry that is reused later */
    brick = new Box(Vector3f.ZERO, brickLength, brickHeight, brickWidth);
    brick.scaleTextureCoordinates(new Vector2f(1f, .5f));
    boxCollisionShape =
     new BoxCollisionShape(new Vector3f(brickLength, brickHeight, brickWidth));
  }

     /** Initialize the materials used in this scene. */
  private void initMaterials()
  {
    wall_mat = new Material(assetManager, "Common/MatDefs/Misc/SimpleTextured.j3md");
    TextureKey key = new TextureKey("Textures/Terrain/BrickWall/BrickWall.jpg");
    key.setGenerateMips(true);
    Texture tex = assetManager.loadTexture(key);
    wall_mat.setTexture("m_ColorMap", tex);
  }

    //////////////////



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
        bulletAppState = new BulletAppState();
        stateManager.attach(getBulletAppState());
        bulletAppState.getPhysicsSpace().setGravity(new Vector3f(0, -0.4f, 0));
        bulletAppState.getPhysicsSpace().setAccuracy(0.01f);
        
        cam.setLocation(new Vector3f(-7f, 11f, 8f));
        cam.lookAt(new Vector3f(0f, 0f, -1f), Vector3f.UNIT_Y);
        FlyByCamera flycam = getFlyByCamera();
        flycam.setMoveSpeed(10f);

        Level lvl = new Level(this);
        lvl.init();

        player = AssetHelper.createActorModel(true);
        rootNode.attachChild(player);

        aiMgr = lvl.getAIManager();
        aiMgr.createEnemy("Enemy 1", new Vector3f(0, 5.0f, 1.5f));
        aiMgr.createEnemy("Enemy 2", new Vector3f(0, 5.0f, 0.0f));
        aiMgr.createEnemy("Enemy 3", new Vector3f(0, 10.0f, 1.5f));



        DirectionalLight sun = new DirectionalLight();
        sun.setDirection(new Vector3f(-0.3f, -0.8f, -1.0f));
        rootNode.addLight(sun);

        initMaterials();
        //makeBrick(new Vector3f(0.0f, 3.0f, 0.0f));
        //for(int i=0; i< 10; ++i)
        //    makeBrick(new Vector3f(0.0f, 15.0f+ 1*i, 0.0f));

    }

    @Override
    public void simpleUpdate(float tpf) {
        DebugRenderer.getInstance().update();
        aiMgr.update(tpf);
        //bulletAppState.getPhysicsSpace().getDynamicsWorld().debugDrawWorld();
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }

    /**
     * @return the bulletAppState
     */
    public BulletAppState getBulletAppState() {
        return bulletAppState;
    }

    public void makeBrick(Vector3f ori)
    {
        /** create a new brick */
        Geometry box_geo = new Geometry("brick", brick);
        box_geo.setMaterial(wall_mat);
        PhysicsNode brickNode = new PhysicsNode(
         box_geo,      // geometry
         boxCollisionShape, // collision shape
         0.1f);       // mass
        /** position the brick and activate shadows */
        brickNode.setLocalTranslation(ori);
        rootNode.attachChild(brickNode);
        bulletAppState.getPhysicsSpace().add(brickNode);
  }
}
