package level;

import ai.AIManager;
import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.Node;
import com.jme3.scene.VertexBuffer.Type;
import com.jme3.texture.Texture;
import com.jme3.util.BufferUtils;
import com.jme3.util.TangentBinormalGenerator;
import game.Game;

/**
 *
 * @author sshake
 */
public class Level
{
    protected Node rootNode;
    protected Mesh terrain;
    protected Game owner;
    protected AIManager aiMgr;

    public Level(Game _owner)
    {
        rootNode = new Node("Level node");
        owner = _owner;
        owner.getRootNode().attachChild(rootNode);

        aiMgr = new AIManager(this);

    }

    public void init()
    {
        initTerrain();
    }

    private void initTerrain()
    {
        terrain = new Mesh();

        Vector3f[] vertices = {
            new Vector3f( -2f,  0f,  -2f), //0
            new Vector3f(  2f,  0f,  -2f),
            new Vector3f( -2f,  0f,   0f),
            new Vector3f(  2f,  0f,   0f),
            new Vector3f( -2f,  0f,   2f),
            new Vector3f(  2f,  0f,   2f), //5
            new Vector3f( -6f,  0f,   0f),
            new Vector3f( -4f,  0f,   0f),
            new Vector3f( -6f,  0f,   2f),
            new Vector3f( -4f,  0f,   2f),
            new Vector3f(  4f,  0f,   0f), //10
            new Vector3f(  6f,  0f,   0f),
            new Vector3f(  4f,  0f,   2f),
            new Vector3f(  6f,  0f,   2f),
            new Vector3f( -6f,  2f,  -6f),
            new Vector3f( -4f,  2f,  -6f), //15
            new Vector3f( -6f,  2f,  -4f),
            new Vector3f( -4f,  2f,  -4f),
            new Vector3f( 4f,  2f,  -6f),
            new Vector3f( 6f,  2f,  -6f),
            new Vector3f( 4f,  2f,  -4f), //20
            new Vector3f( 6f,  2f,  -4f)
        };

        Vector3f[] normals = {
            new Vector3f( 0f,  1f,  0f),
            new Vector3f( 0f,  1f,  0f),
            new Vector3f( 0f,  1f,  0f),
            new Vector3f( 0f,  1f,  0f),
            new Vector3f( 0f,  1f,  0f),
            new Vector3f( 0f,  1f,  0f),
            new Vector3f( 0f,  1f,  0f),
            new Vector3f( 0f,  1f,  0f),
            new Vector3f( 0f,  1f,  0f),
            new Vector3f( 0f,  1f,  0f),
            new Vector3f( 0f,  1f,  0f),
            new Vector3f( 0f,  1f,  0f),
            new Vector3f( 0f,  1f,  0f),
            new Vector3f( 0f,  1f,  0f),
            new Vector3f( 0f,  1f,  0f),
            new Vector3f( 0f,  1f,  0f),
            new Vector3f( 0f,  1f,  0f),
            new Vector3f( 0f,  1f,  0f),
            new Vector3f( 0f,  1f,  0f),
            new Vector3f( 0f,  1f,  0f),
            new Vector3f( 0f,  1f,  0f),
            new Vector3f( 0f,  1f,  0f)
        };

        Vector2f[] texCoords = {
            new Vector2f(0.33f, 0.5f), //0
            new Vector2f(0.67f, 0.5f),
            new Vector2f(0.33f, 0.25f),
            new Vector2f(0.67f, 0.25f),
            new Vector2f(0.33f, 0f),
            new Vector2f(0.67f, 0f), //5
            new Vector2f(0f, 0.25f),
            new Vector2f(0.17f, 0.25f),
            new Vector2f(0f, 0f),
            new Vector2f(0.17f, 0f),
            new Vector2f(0.83f, 0.25f), //10
            new Vector2f(1f, 0.25f),
            new Vector2f(0.83f, 0f),
            new Vector2f(1f, 0f),
            new Vector2f(0f, 1f),
            new Vector2f(0.17f, 1f), //15
            new Vector2f(0f, 0.75f),
            new Vector2f(0.17f, 0.75f),
            new Vector2f(0.83f, 1f),
            new Vector2f(1f, 1f),
            new Vector2f(0.83f, 0.75f),
            new Vector2f(1f, 0.75f)
        };

        int[] triangles = {
            0, 2, 1,
            2, 3, 1,
            4, 3, 2,
            4, 5, 3,
            9, 2, 7,
            9, 4, 2,
            8, 7, 6,
            8, 9, 7,
            5, 10, 3,
            5, 12, 10,
            12, 11, 10,
            12, 13, 11,
            6, 17, 16,
            6, 7, 17,
            10, 21, 20,
            10, 11, 21,
            16, 15, 14,
            16, 17, 15,
            20, 19, 18,
            20, 21, 19,
            17, 18, 15,
            17, 20, 18
        };

        terrain.setBuffer(Type.Position, 3, BufferUtils.createFloatBuffer(vertices));
        terrain.setBuffer(Type.TexCoord, 2, BufferUtils.createFloatBuffer(texCoords));
        //terrain.setBuffer(Type.TexCoord2, 2, BufferUtils.createFloatBuffer(texCoords));
        terrain.setBuffer(Type.Index,    1, BufferUtils.createIntBuffer(triangles));
        terrain.setBuffer(Type.Normal,   3, BufferUtils.createFloatBuffer(normals));

        terrain.scaleTextureCoordinates(new Vector2f(4f,4f));
        terrain.updateBound();
        Geometry terGeom = new Geometry("Terrain", terrain);

        AssetManager assetManager = owner.getAssetManager();
        TangentBinormalGenerator.generate(terrain);           // for lighting effect
        Material mat_lit = new Material(assetManager, "Common/MatDefs/Light/Lighting.j3md");
        Texture pond = assetManager.loadTexture("Textures/Terrain/Pond/Pond.png");
        pond.setWrap(Texture.WrapMode.Repeat);
        mat_lit.setTexture("m_DiffuseMap", pond);
        Texture pondNormal = assetManager.loadTexture("Textures/Terrain/Pond/Pond_normal.png");
        pondNormal.setWrap(Texture.WrapMode.Repeat);
        mat_lit.setTexture("m_NormalMap", pondNormal);
        mat_lit.setFloat("m_Shininess", 10f); // [0,128]
        terGeom.setMaterial(mat_lit);

        //Material mat = new Material(assetManager, "Common/MatDefs/Misc/SolidColor.j3md");
        //mat.setColor("m_Color", ColorRGBA.LightGray);
        //terGeom.setMaterial(mat);

        owner.getRootNode().attachChild(terGeom);
    }

    public Node getRootNode()
    {
        return rootNode;
    }

    public AIManager getAIManager()
    {
        return aiMgr;
    }
}
