package lieb.revoice.festival;

import java.util.Optional;

import de.gurkenlabs.litiengine.Direction;
import de.gurkenlabs.litiengine.Game;
import de.gurkenlabs.litiengine.abilities.Ability;
import de.gurkenlabs.litiengine.abilities.AbilityInfo;
import de.gurkenlabs.litiengine.abilities.effects.Effect;
import de.gurkenlabs.litiengine.abilities.effects.EffectApplication;
import de.gurkenlabs.litiengine.abilities.effects.EffectTarget;
import de.gurkenlabs.litiengine.abilities.effects.ForceEffect;
import de.gurkenlabs.litiengine.entities.CollisionBox;
import de.gurkenlabs.litiengine.entities.Creature;
import de.gurkenlabs.litiengine.entities.EntityPivotType;
import de.gurkenlabs.litiengine.entities.IMobileEntity;
import de.gurkenlabs.litiengine.graphics.animation.Animation;
import de.gurkenlabs.litiengine.physics.Force;
import de.gurkenlabs.litiengine.physics.GravityForce;
import de.gurkenlabs.litiengine.resources.Resources;

@AbilityInfo(cooldown = 100, origin = EntityPivotType.COLLISIONBOX_CENTER, duration = 480, value = 900)
public class Jump extends Ability {

	public Jump(Creature executor) {
		// Calls the Ability constructor
		super(executor);

		// Adds this ability to the Jump effects
		this.addEffect(new JumpEffect(this));				
	}

	private class JumpEffect extends ForceEffect {
		
		protected JumpEffect(Ability ability) {
			super(ability, ability.getAttributes().value().get().intValue(), EffectTarget.EXECUTINGENTITY);
		}

		@Override
		protected Force applyForce(IMobileEntity affectedEntity) {
			// create a new force and apply it to the player
			GravityForce force = new GravityForce(affectedEntity, this.getStrength(), Direction.UP);
			affectedEntity.getMovementController().apply(force); 
			return force;
		}

		@Override
		protected boolean hasEnded(final EffectApplication appliance) {
			return super.hasEnded(appliance) || this.isTouchingCeiling();
		}

		/**
		 * Make sure that the jump is cancelled when the entity touches a static
		 * collision box above it.
		 * 
		 * @return True if the entity touches a static collision box above it.
		 */
		private boolean isTouchingCeiling() {

			Optional<CollisionBox> opt = Game.world().environment().getCollisionBoxes().stream()
					.filter(x -> x.getBoundingBox().intersects(this.getAbility().getExecutor().getBoundingBox()))
					.findFirst();
			if (!opt.isPresent()) {
				return false;
			}

			CollisionBox box = opt.get();
			return box.getCollisionBox().getMaxY() <= this.getAbility().getExecutor().getCollisionBox().getMinY();
		}
	}	
}
