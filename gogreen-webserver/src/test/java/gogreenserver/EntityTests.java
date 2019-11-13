package gogreenserver;

import static org.assertj.core.api.Assertions.assertThat;

import gogreenserver.entity.AchievementList;
import gogreenserver.entity.Achievements;
import gogreenserver.entity.AddSolarpanels;
import gogreenserver.entity.Friend;
import gogreenserver.entity.InsertHistory;
import gogreenserver.entity.InsertHistoryCo2;
import gogreenserver.entity.Records;
import gogreenserver.entity.Tree;
import gogreenserver.entity.User;

import net.bytebuddy.utility.RandomString;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@RunWith(SpringRunner.class)
public class EntityTests {

    private final Random rgn = new Random();

    @Test
    public void achievementList() {
        AchievementList list = new AchievementList();
        List<Achievements> innerList = List.of(new Achievements());
        list.setAchievementsList(innerList);
        assertThat(list.getAchievementsList()).isEqualTo(innerList);
    }

    @Test
    public void achievements() {
        Achievements ach = new Achievements();

        LocalDateTime time = LocalDateTime.now();
        ach.setAchieveData(time);
        assertThat(ach.getAchieveData()).isEqualTo(time);

        String name = RandomString.make();
        ach.setAchievement(name);
        assertThat(ach.getAchievement()).isEqualTo(name);

        String user = RandomString.make();
        ach.setUserName(user);
        assertThat(ach.getUserName()).isEqualTo(user);

        assertThat(ach.toString()).isEqualTo("Achievements{" + "id=null, userName='" + user + '\''
                + ", achievement='" + name + '\'' + ", achieveData=" + time.toString() + '}');
    }

    @Test
    public void addSolarpanels() {
        AddSolarpanels solar = new AddSolarpanels();

        float area = rgn.nextFloat();
        solar.setArea(area);
        assertThat(solar.getArea()).isEqualTo(area);

        LocalDate time = LocalDate.now();
        solar.setDate(time);
        assertThat(solar.getDate()).isEqualTo(time);

        Long panelId = rgn.nextLong();
        solar.setId(panelId);
        assertThat(solar.getId()).isEqualTo(panelId);

        float producedKwh = rgn.nextFloat();
        solar.setProducedKwh(producedKwh);
        assertThat(solar.getProducedKwh()).isEqualTo(producedKwh);

        String username = RandomString.make();
        solar.setUserName(username);
        assertThat(solar.getUserName()).isEqualTo(username);

        assertThat(solar.toString()).isEqualTo(
                "AddSolarpanels{" + "userName='" + username + '\'' + ", area=" + area + ", date="
                        + time + ", id=" + panelId + ", producedKwh=" + producedKwh + '}');
    }

    @Test
    public void insertHistory() {
        InsertHistory his = new InsertHistory();

        LocalDateTime time = LocalDateTime.now();
        his.setInsertDate(time);
        assertThat(his.getInsertDate()).isEqualTo(time);

        String username = RandomString.make();
        his.setUserName(username);
        assertThat(his.getUserName()).isEqualTo(username);

        String actname = RandomString.make();
        his.setActivityName(actname);
        assertThat(his.getActivityName()).isEqualTo(actname);

        float actprice = rgn.nextFloat();
        his.setActivityPrice(actprice);
        assertThat(his.getActivityPrice()).isEqualTo(actprice);

        String altact = RandomString.make();
        his.setAlternateActivity(altact);
        assertThat(his.getAlternateActivity()).isEqualTo(altact);

        float altactprice = rgn.nextFloat();
        his.setAlternateActivityPrice(altactprice);
        assertThat(his.getAlternateActivityPrice()).isEqualTo(altactprice);

        boolean islocal = rgn.nextBoolean();
        his.setActivityIsLocalproduce(islocal);
        assertThat(his.getActivityIsLocalproduce()).isEqualTo(islocal);

        boolean isaltlocal = rgn.nextBoolean();
        his.setAlternateActivityIsLocalproduce(isaltlocal);
        assertThat(his.getAlternateActivityIsLocalproduce()).isEqualTo(isaltlocal);

        float trans = rgn.nextFloat();
        his.setTransportDistanceKm(trans);
        assertThat(his.getTransportDistanceKm()).isEqualTo(trans);

        float duration = rgn.nextFloat();
        his.setEnergyActivityDurationMinutes(duration);
        assertThat(his.getEnergyActivityDurationMinutes()).isEqualTo(duration);

        float eata = rgn.nextFloat();
        his.setEnergyActivityTempAreaM2(eata);
        assertThat(his.getEnergyActivityTempAreaM2()).isEqualTo(eata);

        float eatd = rgn.nextFloat();
        his.setEnergyActivityTempDegreesDecreased(eatd);
        assertThat(his.getEnergyActivityTempDegreesDecreased()).isEqualTo(eatd);

        assertThat(his.toString()).isEqualTo("InsertHistory{" + "userName='" + username + '\''
                + ", activityName='" + actname + '\'' + ", activityPrice=" + actprice
                + ", alternateActivity='" + altact + '\'' + ", alternateActivityPrice="
                + altactprice + ", activityIsLocalproduce=" + islocal
                + ", alternateActivityIsLocalproduce=" + isaltlocal + ", transportDistanceKm="
                + trans + ", energyActivityDurationMinutes=" + duration
                + ", energyActivityTempAreaM2=" + eata + ", energyActivityTempDegreesDecreased="
                + eatd + '}');
    }

    @Test
    public void insertHistoryCO2() {
        InsertHistoryCo2 his = new InsertHistoryCo2();

        float co2saved = rgn.nextFloat();
        his.setCo2Saved(co2saved);
        assertThat(his.getCo2Saved()).isEqualTo(co2saved);

        LocalDateTime time = LocalDateTime.now();
        his.setInsertDate(time);
        assertThat(his.getInsertDate()).isEqualTo(time);

        String username = RandomString.make();
        his.setUserName(username);
        assertThat(his.getUserName()).isEqualTo(username);

        String actname = RandomString.make();
        his.setActivityName(actname);
        assertThat(his.getActivityName()).isEqualTo(actname);

        float actprice = rgn.nextFloat();
        his.setActivityPrice(actprice);
        assertThat(his.getActivityPrice()).isEqualTo(actprice);

        String altact = RandomString.make();
        his.setAlternateActivity(altact);
        assertThat(his.getAlternateActivity()).isEqualTo(altact);

        float altactprice = rgn.nextFloat();
        his.setAlternateActivityPrice(altactprice);
        assertThat(his.getAlternateActivityPrice()).isEqualTo(altactprice);

        boolean islocal = rgn.nextBoolean();
        his.setActivityIsLocalproduce(islocal);
        assertThat(his.getActivityIsLocalproduce()).isEqualTo(islocal);

        boolean isaltlocal = rgn.nextBoolean();
        his.setAlternateActivityIsLocalproduce(isaltlocal);
        assertThat(his.getAlternateActivityIsLocalproduce()).isEqualTo(isaltlocal);

        float trans = rgn.nextFloat();
        his.setTransportDistanceKm(trans);
        assertThat(his.getTransportDistanceKm()).isEqualTo(trans);

        float duration = rgn.nextFloat();
        his.setEnergyActivityDurationMinutes(duration);
        assertThat(his.getEnergyActivityDurationMinutes()).isEqualTo(duration);

        float eata = rgn.nextFloat();
        his.setEnergyActivityTempAreaM2(eata);
        assertThat(his.getEnergyActivityTempAreaM2()).isEqualTo(eata);

        float eatd = rgn.nextFloat();
        his.setEnergyActivityTempDegreesDecreased(eatd);
        assertThat(his.getEnergyActivityTempDegreesDecreased()).isEqualTo(eatd);

        assertThat(his.toString()).isEqualTo("InsertHistoryCo2{" + "insertId=null, insertDate="
                + time + ", userName='" + username + '\'' + ", activityName='" + actname + '\''
                + ", activityPrice=" + actprice + ", alternateActivity='" + altact + '\''
                + ", alternateActivityPrice=" + altactprice + ", activityIsLocalproduce=" + islocal
                + ", alternateActivityIsLocalproduce=" + isaltlocal + ", transportDistanceKm="
                + trans + ", energyActivityDurationMinutes=" + duration
                + ", energyActivityTempAreaM2=" + eata + ", energyActivityTempDegreesDecreased="
                + eatd + ", co2Saved=" + co2saved + '}');
    }

    @Test
    public void records() {
        Records stats = new Records();

        String username = RandomString.make();
        stats.setUserName(username);
        assertThat(stats.getUserName()).isEqualTo(username);

        float solarprod = rgn.nextFloat();
        stats.setKwhProducedSolarpanels(solarprod);
        assertThat(stats.getKwhProducedSolarpanels()).isEqualTo(solarprod);

        float savedco2energy = rgn.nextFloat();
        stats.setSavedCo2Energy(savedco2energy);
        assertThat(stats.getSavedCo2Energy()).isEqualTo(savedco2energy);

        float savedFood = rgn.nextFloat();
        stats.setSavedCo2Food(savedFood);
        assertThat(stats.getSavedCo2Food()).isEqualTo(savedFood);

        float savedsolar = rgn.nextFloat();
        stats.setSavedCo2Solarpanels(savedsolar);
        assertThat(stats.getSavedCo2Solarpanels()).isEqualTo(savedsolar);

        float savedtotal = rgn.nextFloat();
        stats.setSavedCo2Total(savedtotal);
        assertThat(stats.getSavedCo2Total()).isEqualTo(savedtotal);

        float savedtrans = rgn.nextFloat();
        stats.setSavedCo2Transport(savedtrans);
        assertThat(stats.getSavedCo2Transport()).isEqualTo(savedtrans);

        float savedelectro = rgn.nextFloat();
        stats.setSavedKwhEnergy(savedelectro);
        assertThat(stats.getSavedKwhEnergy()).isEqualTo(savedelectro);

        float priceenergy = rgn.nextFloat();
        stats.setSavedPriceEnergy(priceenergy);
        assertThat(stats.getSavedPriceEnergy()).isEqualTo(priceenergy);

        float pricefood = rgn.nextFloat();
        stats.setSavedPriceFood(pricefood);
        assertThat(stats.getSavedPriceFood()).isEqualTo(pricefood);

        float pricesolar = rgn.nextFloat();
        stats.setSavedPriceSolarpanels(pricesolar);
        assertThat(stats.getSavedPriceSolarpanels()).isEqualTo(pricesolar);

        float pricetotal = rgn.nextFloat();
        stats.setSavedPriceTotal(pricetotal);
        assertThat(stats.getSavedPriceTotal()).isEqualTo(pricetotal);

        float pricetrans = rgn.nextFloat();
        stats.setSavedPriceTransport(pricetrans);
        assertThat(stats.getSavedPriceTransport()).isEqualTo(pricetrans);

        float savedtree = rgn.nextFloat();
        stats.setSavedCo2Tree(savedtree);
        assertThat(stats.getSavedCo2Tree()).isEqualTo(savedtree);

        assertThat(stats.toString()).isEqualTo("Records{" + "userName='" + username + '\''
                + ", kwhProducedSolarpanels=" + solarprod + ", savedKwhEnergy=" + savedelectro
                + ", savedCo2Solarpanels=" + savedsolar + ", savedCo2Food=" + savedFood
                + ", savedCo2Energy=" + savedco2energy + ", savedCo2Transport=" + savedtrans
                + ", savedCo2Tree=" + savedtree + ", savedCo2Total=" + savedtotal
                + ", savedPriceFood=" + pricefood + ", savedPriceSolarpanels=" + pricesolar
                + ", savedPriceTransport=" + pricetrans + ", savedPriceEnergy=" + priceenergy
                + ", savedPriceTotal=" + pricetotal + '}');
    }

    @Test
    public void user() {
        User user = new User();

        String username = RandomString.make();
        user.setUsername(username);
        assertThat(user.getUsername()).isEqualTo(username);

        String password = RandomString.make();
        user.setPassword(password);
        assertThat(user.getPassword()).isEqualTo(password);

        String email = RandomString.make();
        user.setEmail(email);
        assertThat(user.getEmail()).isEqualTo(email);

        LocalDate bday = LocalDate.now();
        user.setBdate(bday);
        assertThat(user.getBdate()).isEqualTo(bday);

        LocalDate cday = LocalDate.now();
        user.setDateCreated(cday);
        assertThat(user.getDateCreated()).isEqualTo(cday);

        assertThat(user.toString()).isEqualTo("User{" + "userName='" + username + '\'' + ", email='"
                + email + '\'' + ", bdate='" + bday + '\'' + ", dateCreated='" + cday + '\'' + '}');
    }

    @Test
    public void tree() {
        Tree tree = new Tree();

        String username = RandomString.make();
        tree.setUserName(username);
        assertThat(tree.getUserName()).isEqualTo(username);

        String treetype = RandomString.make();
        tree.setTree(treetype);
        assertThat(tree.getTree()).isEqualTo(treetype);

        LocalDateTime time = LocalDateTime.now();
        tree.setAddingDate(time);
        assertThat(tree.getAddingDate()).isEqualTo(time);

        float co2saved = rgn.nextFloat();
        tree.setCo2Saved(co2saved);
        assertThat(tree.getCo2Saved()).isEqualTo(co2saved);

        assertThat(tree.toString()).isEqualTo("Tree{" + "userName='" + username + "', tree='"
                + treetype + "', addingDate=" + time + ", co2Saved=" + co2saved + '}');
    }

    @Test
    public void friend() {
        Friend friend = new Friend();

        long id = rgn.nextLong();
        friend.setId(id);
        assertThat(friend.getId()).isEqualTo(id);

        String username = RandomString.make();
        friend.setUserName(username);
        assertThat(friend.getUserName()).isEqualTo(username);

        String friendname = RandomString.make();
        friend.setFriendName(friendname);
        assertThat(friend.getFriendName()).isEqualTo(friendname);

        LocalDateTime addtime = LocalDateTime.now();
        friend.setAddTime(addtime);
        assertThat(friend.getAddTime()).isEqualTo(addtime);

        assertThat(friend.toString()).isEqualTo("Friend{" + "id=" + id + ", addTime=" + addtime
                + ", userName='" + username + '\'' + ", friendName='" + friendname + '\'' + '}');
    }

}
