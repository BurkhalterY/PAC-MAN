
        if(peur){
            if(System.currentTimeMillis() - pauseStart >= 6000){
                pauseDuree = System.currentTimeMillis() - pauseStart;
                
                peur = false;
                
                if(blinky.getEtat() == Etat.Attente){
                    blinky.setEtat(Etat.Attente);
                    blinky.setVitesse(0.09375f);
                } else {
                    blinky.setEtat(Etat.Normal);
                    blinky.setVitesse(0.125f);
                }

                if(pinky.getEtat() == Etat.Attente){
                    pinky.setEtat(Etat.Attente);
                    pinky.setVitesse(0.09375f);
                } else {
                    pinky.setEtat(Etat.Normal);
                    pinky.setVitesse(0.125f);
                }
                
                if(inky.getEtat() == Etat.Attente){
                    inky.setEtat(Etat.Attente);
                    inky.setVitesse(0.09375f);
                } else {
                    inky.setEtat(Etat.Normal);
                    inky.setVitesse(0.125f);
                }
                
                if(clyde.getEtat() == Etat.Attente){
                    clyde.setEtat(Etat.Attente);
                    clyde.setVitesse(0.09375f);
                } else {
                    clyde.setEtat(Etat.Normal);
                    clyde.setVitesse(0.125f);
                }
            }
        } else {
            if(phase >= phases.length){
                scatter = false;
            } else {
                if(System.currentTimeMillis() - start - pauseDuree >= phases[phase]*1000){
                    scatter = !scatter;
                    start = System.currentTimeMillis();
                    phase++;
                }
            }
        }

        pacman.setDirection(direction);
        
        
        if(Singleton.getInstance().getMap().mangerGraine(pacman.getX(), pacman.getY())){
            peur = true;
            pauseStart = System.currentTimeMillis();
            
            blinky.setVitesse(0.0625f);
            if(blinky.getEtat() == Etat.Attente){
                blinky.setEtat(Etat.AttenteBleu);
            } else {
                blinky.setEtat(Etat.Peur);
            }
            
            pinky.setVitesse(0.0625f);
            if(pinky.getEtat() == Etat.Attente){
                pinky.setEtat(Etat.AttenteBleu);
            } else {
                pinky.setEtat(Etat.Peur);
            }
            
            inky.setVitesse(0.0625f);
            if(inky.getEtat() == Etat.Attente){
                inky.setEtat(Etat.AttenteBleu);
            } else {
                inky.setEtat(Etat.Peur);
            }
            
            clyde.setVitesse(0.0625f);
            if(clyde.getEtat() == Etat.Attente){
                clyde.setEtat(Etat.AttenteBleu);
            } else {
                clyde.setEtat(Etat.Peur);
            }
        }
        
        if(peur){
            if(blinky.touherPacman(pacman.getX(), pacman.getY())){
                blinky.setEtat(Etat.Retour);
            }
            if(pinky.touherPacman(pacman.getX(), pacman.getY())){
                pinky.setEtat(Etat.Retour);
            }
            if(inky.touherPacman(pacman.getX(), pacman.getY())){
                inky.setEtat(Etat.Retour);
            }
            if(clyde.touherPacman(pacman.getX(), pacman.getY())){
                clyde.setEtat(Etat.Retour);
            }
        } else {
            if(blinky.touherPacman(pacman.getX(), pacman.getY())
            || pinky.touherPacman(pacman.getX(), pacman.getY())
            || inky.touherPacman(pacman.getX(), pacman.getY())
            || clyde.touherPacman(pacman.getX(), pacman.getY())){
                run = false;
            }
        }