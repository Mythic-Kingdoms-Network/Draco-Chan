package com.avairebot.orion.commands.fun;

import com.avairebot.orion.Constants;
import com.avairebot.orion.Orion;
import com.avairebot.orion.Statistics;
import com.avairebot.orion.contracts.commands.AbstractCommand;
import com.avairebot.orion.factories.MessageFactory;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;

import java.awt.*;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RipCommand extends AbstractCommand {

    public RipCommand(Orion orion) {
        super(orion);
    }

    @Override
    public String getName() {
        return "RIP Command";
    }

    @Override
    public String getDescription() {
        return "Pay your respects";
    }

    @Override
    public List<String> getUsageInstructions() {
        return Collections.singletonList("`>rip` - Pay your respects");
    }

    @Override
    public String getExampleUsage() {
        return "`>rip`";
    }

    @Override
    public List<String> getTriggers() {
        return Collections.singletonList("rip");
    }

    @Override
    public List<String> getMiddleware() {
        return Arrays.asList("throttle:user,1,5");
    }

    @Override
    public boolean onCommand(Message message, String[] args) {
        int totalRespects = getTotalRespects();
        Statistics.addRespects();

        try {
            orion.database.queryUpdate(
                    String.format("UPDATE `%s` SET `respects` = `respects` + 1;", Constants.STATISTICS_TABLE_NAME)
            );
        } catch (SQLException ex) {
            return false;
        }

        EmbedBuilder embed = MessageFactory.createEmbeddedBuilder()
                .setColor(Color.decode("#2A2C31"))
                .setDescription(String.format("**%s** has paid their respects.", message.getMember().getEffectiveName()))
                .setFooter(String.format("%s Today, %s Overall",
                        Statistics.getRespects(), totalRespects
                ), null);

        message.getChannel().sendMessage(embed.build()).queue();
        return true;
    }

    private int getTotalRespects() {
        try {
            return orion.database.newQueryBuilder(Constants.STATISTICS_TABLE_NAME).get().first()
                    .getInt("respects", 0) + 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return 1;
        }
    }
}
