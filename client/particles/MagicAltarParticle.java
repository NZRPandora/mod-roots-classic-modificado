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

public class MagicAltarParticle
extends TextureSheetParticle {
    public double colorR = 0.0;
    public double colorG = 0.0;
    public double colorB = 0.0;

    public MagicAltarParticle(ClientLevel levelAccessor, double x, double y, double z, double vx, double vy, double vz, double r, double g, double b, SpriteSet sprite) {
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
        this.lifetime = 8;
        this.xd = vx;
        this.yd = vy;
        this.zd = vz;
        this.quadSize = 0.05f;
        this.pickSprite(sprite);
    }

    public void tick() {
        super.tick();
        this.xd *= 0.9;
        this.yd += 0.01;
        this.zd *= 0.9;
        if (this.random.nextInt(4) >= 2 && this.age > 0) {
            --this.age;
        }
        float lifeCoeff = ((float)this.lifetime - (float)this.age) / (float)this.lifetime;
        this.rCol = Math.min(1.0f, (float)this.colorR * (1.5f - lifeCoeff) + lifeCoeff);
        this.gCol = Math.min(1.0f, (float)this.colorG * (1.5f - lifeCoeff) + lifeCoeff);
        this.bCol = Math.min(1.0f, (float)this.colorB * (1.5f - lifeCoeff) + lifeCoeff);
        this.alpha = lifeCoeff;
        this.quadSize = 0.1f * lifeCoeff;
        if ((double)lifeCoeff > 0.5) {
            this.quadSize = 0.1f * (0.5f + 4.0f * (1.0f - lifeCoeff));
        }
        if ((double)lifeCoeff <= 0.5) {
            this.quadSize = 0.1f * (5.0f * lifeCoeff);
        }
    }

    public ParticleRenderType getRenderType() {
        return ParticleRenderTypes.MAGIC_RENDER;
    }

    public boolean isAlive() {
        return this.age < this.lifetime;
    }
}

