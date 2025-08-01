package agzam4.ui.editor;

import agzam4.ui.MobileUI.MobileButtons;
import agzam4.ui.ModStyles;
import arc.func.Boolc;
import arc.func.Boolf;
import arc.func.Boolp;
import arc.math.geom.Point2;
import arc.scene.ui.TextButton;
import arc.scene.ui.layout.Table;
import arc.util.*;
import static agzam4.ui.MobileUI.tilesize;

public class ButtonProps {

	public final String name;

	public @Nullable Runnable onClick;
	public @Nullable Boolc onToggle;
	public @Nullable Boolp toggled;
	
	public Point2 position = new Point2(0, 0);
	public String text = "";
	public boolean collapseable = true;

	public ButtonProps() {
		this.name = MobileButtons.empty.prop.name;
	}
	
	public ButtonProps(String name, Runnable onClick) {
		this.name = name;
		this.onClick = onClick;
	}

	public ButtonProps(String name, Boolc onToggle) {
		this.name = name;
		this.onToggle = onToggle;
	}
	
	
	public boolean isToggle() {
		return onToggle != null;
	}

	
	public ButtonProps toggled(Boolp toggled) {
		this.toggled = toggled;
		return this;
	}
	
	public ButtonProps position(int x, int y) {
		position.x = x;
		position.y = y;
		return this;
	}

	public ButtonProps icon(char c) {
		text = Character.toString(c);
		return this;
	}

	public TextButton button(boolean listener) {
		boolean empty = name.isEmpty() || name.equals(MobileButtons.empty.name());
        TextButton button = new TextButton(text, isToggle() ? ModStyles.mobileToggle : ModStyles.mobileButton); // Styles.logicTogglet Styles.grayt
        if(listener) button.changed(() -> {
        	if(onToggle != null) onToggle.get(button.isChecked());
        	if(onClick != null) onClick.run();
        });
        button.setDisabled(empty && listener);
        return button;
	}
	
	public void button2(Table table) {
        TextButton button = new TextButton(text, isToggle() ? ModStyles.mobileToggle : ModStyles.mobileButton);
        button.changed(() -> {
        	if(onToggle != null) onToggle.get(button.isChecked());
        	if(onClick != null) onClick.run();
        });
        button.setDisabled(name.isEmpty());
        var cell = table.add(button).margin(0).pad(0);
        cell.update(t -> {
        	t.setBounds(position.x * tilesize, -position.y * tilesize, tilesize, tilesize);
        	cell.setBounds(position.x * tilesize, -position.y * tilesize, tilesize, tilesize);
        });
	}

	public int x() {
		return position.x;
	}

	public int y() {
		return position.y;
	}

	@Override
	public String toString() {
		return Strings.format("button [gold]@[] (@,@)", name, position.x * tilesize, position.y * tilesize);
	}
	

	public ButtonProps collapseable(boolean collapseable) {
		this.collapseable = collapseable;
		return this;
	}

	public boolean toggle() {
		if(toggled == null) return false;
		return toggled.get();
	}
}
