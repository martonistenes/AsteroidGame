package view;

import characters.Settler;
import materials.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * InterfacePanel osztály
 */
public class InterfacePanel extends Drawable {

    /**
     * Output
     */
    private String output;

    /**
     * Inputra várakozó telepes
     */
    private Settler waitingSettler;

    /**
     * Nyersanyok képei
     */
    private Image CoalImg, IronImg, WaterIceImg, UraniumImg;

    /**
     * TextBox színe
     */
    private Color textbox;

    /**
     * Barna szín
     */
    private final Color brown = new Color(128,64,0);

    /**
     * Lépett-e a telepes
     */
    private boolean stepped = false;

    /**
     * Gombok az interfacepanelen
     */
    private final Map<String, Button> buttonMap;

    /**
     * Szöveg betűtípusa
     */
    private static final Font font;

    static {
        font = new Font("Dialog",Font.PLAIN,29);
    }

    /**
     * Konstruktor
     */
    public InterfacePanel(){
        this.zIndex = 100;
        output = "";
        textbox = brown;

        this.buttonMap = new HashMap<>();
        this.buttonMap.put("Robot", new Button(new Rectangle(0,434,120,43), new Rectangle(5,439,110,33),"Robot",26));
        this.buttonMap.put("CraftTeleport", new Button(new Rectangle(0,477,120,43), new Rectangle(5,482,110,33),"Teleport",26));
        this.buttonMap.put("PlaceTeleport", new Button(new Rectangle(880,305,120,43), new Rectangle(885,310,110,33),"Teleport",26));
        this.buttonMap.put("Craft", new Button(new Rectangle(0,520,120,43), new Rectangle(5,525,110,33),"Craft",26));
        this.buttonMap.put("Drill", new Button(new Rectangle(120,520,120,43), new Rectangle(125,525,110,33),"Drill",26));
        this.buttonMap.put("Mine", new Button(new Rectangle(760,520,120,43), new Rectangle(765,525,110,33),"Mine",26));
        this.buttonMap.put("Place", new Button(new Rectangle(880,520,120,43), new Rectangle(885,525,110,33),"Place",26));
        this.buttonMap.put("Iron", new Button(new Rectangle(880,391,120,43), new Rectangle(885,396,110,33),"Iron",26));
        this.buttonMap.put("Coal", new Button(new Rectangle(880,348,120,43), new Rectangle(885,353,110,33),"Coal",26));
        this.buttonMap.put("Waterice", new Button(new Rectangle(880,434,120,43), new Rectangle(885,439,110,33),"Waterice",26));
        this.buttonMap.put("Uranium", new Button(new Rectangle(880,477,120,43), new Rectangle(885,482,110,33),"Uranium",26));
        this.buttonMap.put("Output", new Button(new Rectangle(240,520,520,43), new Rectangle(245,525,510,33),output,29));

        this.buttonMap.get("Drill").SetCallback(() -> {
            if (waitingSettler.Drill()){
                output = "Sikerült fúrni!";
                stepped = true;
                SettlerView sw = ViewController.getInstance().GetSettlerView(ViewController.getInstance().GetCurrentSettlerWaitingForInput());
                ViewController.getInstance().EventHappened(sw.CharacterToString() + " drilled!");
                ViewController.getInstance().SettlerStepped();
            } else {
                output = "Nem sikerült fúrni!";
            }
        });
        this.buttonMap.get("Mine").SetCallback(() -> {
            if (waitingSettler.Mine()){
                output = "Sikerült bányászni!";
                stepped = true;
                SettlerView sw = ViewController.getInstance().GetSettlerView(ViewController.getInstance().GetCurrentSettlerWaitingForInput());
                ViewController.getInstance().EventHappened(sw.CharacterToString() + " mined!");
                ViewController.getInstance().SettlerStepped();
            } else {
                output = "Nem sikerült bányászni!";
            }
        });
        this.buttonMap.get("Robot").SetCallback(() -> {
            if(buttonMap.get("Craft").GetState()) {
                if (waitingSettler.CraftRobot()) {
                    output = "Sikerült a Robot craftolása!";
                    stepped = true;
                    SettlerView sw = ViewController.getInstance().GetSettlerView(ViewController.getInstance().GetCurrentSettlerWaitingForInput());
                    ViewController.getInstance().EventHappened(sw.CharacterToString() + " crafted robot!");
                    ViewController.getInstance().SettlerStepped();
                } else {
                    output = "Nem sikerült a Robot Craftolása!";
                }
            }
        });
        this.buttonMap.get("CraftTeleport").SetCallback(() -> {
            if(buttonMap.get("Craft").GetState()) {
                if (waitingSettler.CraftTeleportGates()) {
                    output = "Sikerült a Teleport craftolása!";
                    stepped = true;
                    SettlerView sw = ViewController.getInstance().GetSettlerView(ViewController.getInstance().GetCurrentSettlerWaitingForInput());
                    ViewController.getInstance().EventHappened(sw.CharacterToString() + " crafted teleport!");
                    ViewController.getInstance().SettlerStepped();
                } else {
                    output = "Nem sikerült a Teleport Craftolása!";
                }
            }
        });
        this.buttonMap.get("Iron").SetCallback(() -> {
            if(this.buttonMap.get("Place").GetState()) {
                if (waitingSettler.PlaceMaterial(new Iron())) {
                    output = "Sikerült letenni a Vasat!";
                    stepped=true;
                    SettlerView sw = ViewController.getInstance().GetSettlerView(ViewController.getInstance().GetCurrentSettlerWaitingForInput());
                    ViewController.getInstance().EventHappened(sw.CharacterToString() + " placed iron!");
                    ViewController.getInstance().SettlerStepped();
                } else {
                    output = "Nem sikerült letenni a Vasat!";
                }
            }
        });
        this.buttonMap.get("Coal").SetCallback(() -> {
            if(this.buttonMap.get("Place").GetState()) {
                if (waitingSettler.PlaceMaterial(new Coal())) {
                    output = "Sikerült letenni a Szenet!";
                    stepped=true;
                    SettlerView sw = ViewController.getInstance().GetSettlerView(ViewController.getInstance().GetCurrentSettlerWaitingForInput());
                    ViewController.getInstance().EventHappened(sw.CharacterToString() + " placed coal!");
                    ViewController.getInstance().SettlerStepped();
                } else {
                    output = "Nem sikerült letenni a Szenet!";
                }
            }
        });
        this.buttonMap.get("Waterice").SetCallback(() -> {
            if(this.buttonMap.get("Place").GetState()) {
                if (waitingSettler.PlaceMaterial(new WaterIce())) {
                    output = "Sikerült letenni a Vízjeget!";
                    stepped=true;
                    SettlerView sw = ViewController.getInstance().GetSettlerView(ViewController.getInstance().GetCurrentSettlerWaitingForInput());
                    ViewController.getInstance().EventHappened(sw.CharacterToString() + " placed waterice!");
                    ViewController.getInstance().SettlerStepped();
                } else {
                    output = "Nem sikerült letenni a Vízjeget!";
                }
            }
        });
        this.buttonMap.get("Uranium").SetCallback(() -> {
            if(this.buttonMap.get("Place").GetState()) {
                if (waitingSettler.PlaceMaterial(new Uranium())) {
                    output = "Sikerült letenni az Uránt!";
                    stepped=true;
                    SettlerView sw = ViewController.getInstance().GetSettlerView(ViewController.getInstance().GetCurrentSettlerWaitingForInput());
                    ViewController.getInstance().EventHappened(sw.CharacterToString() + " placed uranium!");
                    ViewController.getInstance().SettlerStepped();
                } else {
                    output = "Nem sikerült letenni az Uránt!";
                }
            }
        });
        this.buttonMap.get("PlaceTeleport").SetCallback(() -> {
            if(this.buttonMap.get("Place").GetState()) {
                if (waitingSettler.PlaceTeleportGate()) {
                    output = "Sikerült letenni a Teleportkaput!";
                    stepped = true;
                    SettlerView sw = ViewController.getInstance().GetSettlerView(ViewController.getInstance().GetCurrentSettlerWaitingForInput());
                    ViewController.getInstance().EventHappened(sw.CharacterToString() + " placed teleport!");
                    ViewController.getInstance().SettlerStepped();
                } else {
                    output = "Nem sikerült letenni a Teleportkaput!";
                }
            }
        });
        this.buttonMap.get("Output").SetCallback(() -> {
            // :woozy_face:
            output = "Itt meg mit szeretnél?";
        });


        try{
            //Beolvasas utan automatikusan bezarodnak a fajlok az ImageIO-nal
            CoalImg = ImageIO.read(new File("Textures/szén.png"));
            IronImg = ImageIO.read(new File("Textures/vas.png"));
            WaterIceImg = ImageIO.read(new File("Textures/vízjég.png"));
            UraniumImg = ImageIO.read(new File("Textures/urán.png"));
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
    }

    /**
     * Kirajzolás
     * @param graphics: graphics
     * @param cameraPos: kameria pozíciója
     */
    @Override
    public void Draw(Graphics2D graphics, Position cameraPos) { // boolean-eket itt nagyban használnám
        if (buttonMap.get("Craft").GetState()){ // mágikus számok hada 1.
            // Robot craftoló gomb
            this.buttonMap.get("Robot").Draw(graphics);

            // Teleport craftoló gomb
            this.buttonMap.get("CraftTeleport").Draw(graphics);

            //Craft gomb meg van nyomva
            this.buttonMap.get("Craft").Draw(graphics);

        } else {  // mágikus számok hada 2.

            //Craft gomb nincs megnyomva
            this.buttonMap.get("Craft").Draw(graphics);
        }

        if (buttonMap.get("Place").GetState()) { // mágikus számok hada 3.

            /// Teleportkaput letevő gomb
            this.buttonMap.get("PlaceTeleport").Draw(graphics);

            // Szenet letevő gomb
            this.buttonMap.get("Coal").Draw(graphics);

            // Vasat letevő gomb
            this.buttonMap.get("Iron").Draw(graphics);

            // Vízjeget letevő gomb // emiatt lett 120 a szélessége minden gombnak
            this.buttonMap.get("Waterice").Draw(graphics);

            // Uránt letevő gomb
            this.buttonMap.get("Uranium").Draw(graphics);

            //Place gomb meg van nyomva
            this.buttonMap.get("Place").Draw(graphics);

        } else {    // mágikus számok hada 4.

            //Place gomb nincs megnyomva
            this.buttonMap.get("Place").Draw(graphics);
        }

        // Nyersanyagok kijelzése

        graphics.setColor(brown);
        Position winSize = ViewController.getInstance().GetWindowSize();

        graphics.fillRect(240*winSize.x/1000,0*winSize.y/563,520*winSize.x/1000,43*winSize.y/563);
        graphics.setColor(Color.LIGHT_GRAY);
        graphics.fillRect(245*winSize.x/1000,5*winSize.y/563,510*winSize.x/1000,33*winSize.y/563);
        graphics.setFont(font);
        graphics.setColor(Color.BLACK);
        graphics.drawImage(CoalImg,245*winSize.x/1000,2*winSize.y/563,40*winSize.x/1000,40*winSize.y/563,null,null);
        graphics.drawImage(IronImg,340*winSize.x/1000,2*winSize.y/563,40*winSize.x/1000,40*winSize.y/563,null,null);
        graphics.drawImage(WaterIceImg,435*winSize.x/1000,2*winSize.y/563,40*winSize.x/1000,40*winSize.y/563,null,null);
        graphics.drawImage(UraniumImg,530*winSize.x/1000,2*winSize.y/563,40*winSize.x/1000,40*winSize.y/563,null,null);

        if (waitingSettler!=null) {

            int coal = 0;
            int iron = 0;
            int waterice = 0;
            int uranium = 0;
            var materials = waitingSettler.GetInventory().GetMaterials();
            for ( var material: materials
                 ) {
                if (material.CompatibleWith(new Coal()))
                    coal++;
                else if (material.CompatibleWith(new Iron()))
                    iron++;
                else if (material.CompatibleWith(new WaterIce()))
                    waterice++;
                else
                    uranium++;
            }


            graphics.drawString(coal+"",295*winSize.x/1000,32*winSize.y/563);
            graphics.drawString(iron+"",390*winSize.x/1000,32*winSize.y/563);
            graphics.drawString(waterice+"",485*winSize.x/1000,32*winSize.y/563);
            graphics.drawString(uranium+"",580*winSize.x/1000,32*winSize.y/563);
        } else {
            graphics.drawString("0",295*winSize.x/1000,32*winSize.y/563);
            graphics.drawString("0",390*winSize.x/1000,32*winSize.y/563);
            graphics.drawString("0",485*winSize.x/1000,32*winSize.y/563);
            graphics.drawString("0",580*winSize.x/1000,32*winSize.y/563);
        }
        // Ast_Infobox?

        // Szöveges visszacsatolás
        if (waitingSettler!=null)
            textbox = ViewController.getInstance().GetSettlerView(waitingSettler).GetColor();

        Button outputButton = this.buttonMap.get("Output");
        outputButton.SetBackGroundColor(this.textbox);
        outputButton.SetString(this.output);
        outputButton.Draw(graphics);

        // Drill gomb
        this.buttonMap.get("Drill").Draw(graphics);

        // Mine gomb
        this.buttonMap.get("Mine").Draw(graphics);
    }

    /**
     * Beállítja az akutálisan várakozó telepest
     * @param currentWaitingSettler: akutálisan várakozó telepes
     */
    public void SetCurrentWaitingSettler(Settler currentWaitingSettler) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        waitingSettler=currentWaitingSettler;
        stepped=false;

        output = "Te következel!";
    }

    /**
     * Ellenőrzi a kattintást
     * @param clickPos: kattintás pozíciója
     * @return le lett kattintva
     */
    public boolean HandleClick(Position clickPos){
        if(waitingSettler == null){
            output = "Ilyen nincs, nem jön semelyik Settler!";
            return true;
        }
        if (stepped){
            output = "Már jöttél, ne siess!";
            return true;
        }
        boolean clickedOnInterface = false;

        var res = buttonMap.entrySet().stream()
                .filter(stringButtonEntry -> stringButtonEntry.getValue().ClickedCheck(clickPos, null)).findAny();
        if(res.isPresent()) {
            res.get().getValue().Clicked(clickPos, null);
            if(buttonMap.get("Place").GetState() && buttonMap.get("Craft").GetState())
                clickedOnInterface = true;

            buttonMap.entrySet().forEach(stringButtonEntry -> {
                if(!stringButtonEntry.equals(res.get()))
                    stringButtonEntry.getValue().UnClicked();
            });
        } else {
            buttonMap.forEach((s, button) -> button.UnClicked());
        }

        return !clickedOnInterface;
    }
}
