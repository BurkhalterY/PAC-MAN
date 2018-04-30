var fholdd, fholdu, fholdl, fholdr, cc;
holdd=keyboard_check(vk_down)
holdu=keyboard_check(vk_up)
holdl=keyboard_check(vk_left)
holdr=keyboard_check(vk_right)

if holdu && holdd {
holdu=0
holdd=0 }
if holdl && holdr {
holdl=0
holdr=0 }

if (holdd ||holdu || holdl || holdr)
{
	if (!moving) {
		if (holdd) {
			sprite_index=argument4; global.facing="D"
		} else if (holdu) {
			sprite_index=argument5; global.facing="U"
		}
		if (holdl) {
			sprite_index=argument6; global.facing="L"
		} else if (holdr) {
			sprite_index=argument7; global.facing="R"
		}
	}
	moving = 1
	if (holdd && !holdl && !holdr) {
		sprite_index=argument4; global.facing="D"
	}
	else if (holdu && !holdl && !holdr) {
		sprite_index=argument5; global.facing="U"
	}
	else if (holdl && !holdd && !holdu) {
		sprite_index=argument6; global.facing="L"
	}
	else if (holdr && !holdd && !holdu) {
		sprite_index=argument7; global.facing="R"
	}
	if (holdd && holdl && sprite_index!=argument4 && sprite_index!=argument6){
		sprite_index=argument6; global.facing="L"
	} else if (holdd && holdr && sprite_index!=argument4 && sprite_index!=argument7) {
		sprite_index=argument7; global.facing="R"
	} else if (holdu && holdl && sprite_index!=argument5 && sprite_index!=argument6) {
		sprite_index=argument6; global.facing="L"
	} else if (holdu && holdr && sprite_index!=argument5 && sprite_index!=argument7) {
		sprite_index=argument7; global.facing="R"
	}
} else {
	moving=0
}
	if (moving) {
		if ((holdd || holdu) && (holdl || holdr)){
			movestep+=(movespeed/sqrt(2))
		} else {
			movestep+=movespeed
		}
		while (movestep>=1) {
			movestep-=1
			fholdd=0; fholdu=0; fholdl=0; fholdr=0;
			for (cc=6; cc>0; cc-=1) {
				if holdu {
					if !place_free(x,y-1) {
						if place_free(x-cc,y-1) && !(holdr || fholdr) {
							fholdl=1
						} else if place_free(x+cc,y-1) && !(holdl || fholdl) {
							fholdr=1
						}
					}
				} else if holdd {
					if !place_free(x,y+1) {
						if place_free(x-cc,y+1) && !(holdr || fholdr) {
							fholdl=1
						} else if place_free(x+cc,y+1) && !(holdl || fholdl) {
							fholdr=1
						}
					}
				}
				if holdl {
					if !place_free(x-1,y) {
						if place_free(x-1,y-cc) && !(holdd || fholdd) {
							fholdu=1
						} else if place_free(x-1,y+cc) && !(holdu || fholdu) {
							fholdd=1
						}
					}
				} else if holdr {
					if !place_free(x+1,y) {
						if place_free(x+1,y-cc) && !(holdd || fholdd) {
							fholdu=1
						} else if place_free(x+1,y+cc) && !(holdu || fholdu) {
							fholdd=1
						}
					}
				}
			}
			xstep=(holdr || fholdr)-(holdl || fholdl)
			ystep=(holdd || fholdd)-(holdu || fholdu)
			if (place_free(x+xstep,y)) {
				x+=xstep
			}
			if (place_free(x,y+ystep)) {
				y+=ystep
			}
		}
	} else {
		switch (sprite_index) {
			case argument4: sprite_index=argument0; break;
			case argument5: sprite_index=argument1; break;
			case argument6: sprite_index=argument2; break;
			case argument7: sprite_index=argument3; break;
		}
	movestep=0
}