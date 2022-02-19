package ANWAQ.plankalerts;

import net.runelite.api.NPC;

import java.util.List;

public class RoomIdentifier {
    enum Mobs {
        GUARDIANS,
        LITTLE_MUTTA,
        BIG_MUTTA,
        MISC_MUTTA,
        ICE_DEMOM,
        TEKTON,
        OLM,
        CRAB,
        TIGHTROPE,
        SHAMANS,
        MYSTICS,
        VANGUARDS,
        VASA,
        VESPULA,
        MAIDEN,
        BLOAT,
        NYLO,
        SOTETSEG,
        XARPUS,
        VERZIK
    }

    public static Mobs findRoom(List<NPC> NPCs) {
        for (NPC NPC : NPCs) {
            switch (NPC.getId()) {
                case 7554:
                case 7555:
                case 7553:
                case 7550:
                case 7551:
                case 7552:
                    return Mobs.OLM;
                case 7540:
                case 7545:
                case 7541:
                case 7542:
                case 7543:
                case 7544:
                    return Mobs.TEKTON;
                case 7562:
                    return Mobs.LITTLE_MUTTA;
                case 7563:
                    return Mobs.BIG_MUTTA;
                case 7561:
                    return Mobs.MISC_MUTTA;
                case 7573:
                case 7574:
                    return Mobs.SHAMANS;
                case 7604:
                case 7605:
                case 7606:
                    return Mobs.MYSTICS;
                case 7559:
                case 7560:
                    return Mobs.TIGHTROPE;
                case 7584:
                case 7585:
                    return Mobs.ICE_DEMOM;
                case 7576:
                case 7577:
                case 7578:
                case 7579:
                    return Mobs.CRAB;
                case 7569:
                case 7570:
                    return Mobs.GUARDIANS;
                case 7525:
                case 7526:
                case 7527:
                case 7528:
                case 7529:
                    return Mobs.VANGUARDS;
                case 7530:
                case 7531:
                case 7532:
                    return Mobs.VESPULA;
                case 7566:
                case 7567:
                    return Mobs.VASA;
                    /*
                        TOB NPC ID's untested!
                     */
                case 8360:
                case 8361:
                case 8362:
                case 8363:
                case 8364:
                case 8365:
                    return Mobs.MAIDEN;
                case 8359:
                case 10812:
                case 10813:
                case 11184:
                    return Mobs.BLOAT;
                case 8354:
                case 8355:
                case 8356:
                case 8357:
                case 10786:
                case 10787:
                case 10788:
                case 10789:
                case 10807:
                case 10808:
                case 10809:
                case 10810:
                case 11185:
                    return Mobs.NYLO;
                case 8387:
                case 8388:
                case 10864:
                case 10865:
                case 10867:
                case 10868:
                case 11186:
                    return Mobs.SOTETSEG;
                case 8338:
                case 8339:
                case 8340:
                case 8341:
                case 10766:
                case 10767:
                case 10768:
                case 10769:
                case 10770:
                case 10771:
                case 10772:
                case 10773:
                case 11187:
                    return Mobs.XARPUS;
                case 8250:
                case 8369:
                case 8370:
                case 8371:
                case 8372:
                case 8373:
                case 8374:
                case 8375:
                case 10830:
                case 10831:
                case 10832:
                case 10833:
                case 10834:
                case 10835:
                case 10836:
                case 10847:
                case 10848:
                case 10849:
                case 10850:
                case 10851:
                case 10852:
                case 10853:
                case 11178:
                case 11179:
                    return Mobs.VERZIK;

            }
        }
        return null;
    }
}