/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.client.multiplayer.ClientLevel
 *  net.minecraft.client.particle.ParticleRenderType
 *  net.minecraft.client.particle.SpriteSet
 *  net.minecraft.client.particle.TextureSheetParticle
 */
package elucent.rootsclassic.client.particles;

import elucent.rootsclassic.client.particles.factory.ParticleRenderTypes;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.TextureSheetParticle;

public class MagicAltarLineParticle
extends TextureSheetParticle {
    public double colorR = 0.0;
    public double colorG = 0.0;
    public double colorB = 0.0;

    public MagicAltarLineParticle(ClientLevel levelAccessor, double x, double y, double z, double vx, double vy, double vz, double r, double g, double b, SpriteSet sprite) {
        super(levelAccessor, x, y, z, 0.0, 0.0, 0.0);
        this.colorR = r;
        this.colorG = g;
        this.colorB = b;
        if (this.colorR > 1.0) {
            this.colorR /= 255.0;
        }
        if (this.colorG > 1.0) {
            this.colorG /= 255.0;
        }
        if (this.colorB > 1.0) {
            this.colorB /= 255.0;
        }
        this.setColor(1.0f, 1.0f, 1.0f);
        this.lifetime = 16;
        this.gravity = 0.0f;
        this.quadSize = 0.05f;
        this.xd = (vx - x) / (double)this.lifetime;
        this.yd = (vy - y) / (double)this.lifetime;
        this.zd = (vz - z) / (double)this.lifetime;
        this.alpha = 0.0f;
        this.pickSprite(sprite);
    }

    public void tick() {
        super.tick();
        float lifeCoeff = ((float)this.lifetime - (float)this.age) / (float)this.lifetime;
        this.rCol = Math.min(1.0f, (float)this.colorR * (1.5f - lifeCoeff) + lifeCoeff);
        this.gCol = Math.min(1.0f, (float)this.colorG * (1.5f - lifeCoeff) + lifeCoeff);
        this.bCol = Math.min(1.0f, (float)this.colorB * (1.5f - lifeCoeff) + lifeCoeff);
        this.alpha = 1.0f - lifeCoeff;
        this.quadSize = 0.1f * (0.5f + 2.0f * (1.0f - lifeCoeff));
    }

    public ParticleRenderType getRenderType() {
        return ParticleRenderTypes.MAGIC_RENDER;
    }

    public boolean isAlive() {
        return this.age < this.lifetime;
    }
}

