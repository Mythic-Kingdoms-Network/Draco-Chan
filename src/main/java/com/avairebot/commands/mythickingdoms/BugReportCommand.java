/*
 * Copyright (c) 2018.
 *
 * This file is part of AvaIre.
 *
 * AvaIre is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * AvaIre is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with AvaIre.  If not, see <https://www.gnu.org/licenses/>.
 *
 *
 */

package com.avairebot.commands.mythickingdoms;

import com.avairebot.AvaIre;
import com.avairebot.commands.CommandMessage;
import com.avairebot.contracts.commands.Command;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class BugReportCommand extends Command {

    public BugReportCommand(AvaIre avaire) {
        super(avaire);
    }

    @Override
    public String getName() {
        return "Bug Report Command";
    }

    @Override
    public String getDescription() {
        return "Can be used to report a bug from a MKN modpack.";
    }

    @Override
    public List<String> getUsageInstructions() {
        return Collections.singletonList("`:command` - Reports a bug of a MKN modpack");
    }

    @Override
    public List<String> getTriggers() {
        return Arrays.asList("bugreport", "reportbug", "bug");
    }

    @Override
    public boolean onCommand(CommandMessage context, String[] args) {
        context.getMessage().getChannel().sendTyping().queue(v -> {

            context.getAuthor().openPrivateChannel().queue((channel) -> {
                channel.sendMessage("You used the command bugreport ;)").queue();
            });
        });
        return true;
    }
}
