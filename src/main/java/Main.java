import com.jagrosh.jdautilities.command.Command;
import com.jagrosh.jdautilities.command.CommandClientBuilder;
import com.jagrosh.jdautilities.command.CommandEvent;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.OnlineStatus;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import java.util.*;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;


import javax.security.auth.login.LoginException;

public class Main extends ListenerAdapter {
    public static void main(String[] args) throws LoginException {
        CommandClientBuilder commandClientBuilder = new CommandClientBuilder();
        commandClientBuilder.setPrefix("$"); //Command prefix for bot
        commandClientBuilder.addCommands(new KickCommand(), new HiCommand());
        commandClientBuilder.setOwnerId("BOT ID"); //ID HERE
        commandClientBuilder.setGame(Game.listening("Music"));

        new JDABuilder(AccountType.BOT)
                .setToken("TOKEN") //Login Token Here
                .addEventListener(new Main())
                .addEventListener(commandClientBuilder.build())
                .buildAsync()
                .getPresence().setPresence(OnlineStatus.ONLINE,Game.listening("Music"));


    }
    @Override
    public void onMessageReceived(MessageReceivedEvent event){
        if (event.getAuthor().isBot()){
            return;
        }

        if (event.getMessage().getContentRaw().equals("<:POGGERS:433430254384840726>")){
            event.getChannel().sendMessage("<:POGGERS:433430254384840726>").queue();
        }

        //Kicks Mylon if "wat"
        if (event.getMessage().getContentRaw().equalsIgnoreCase("wat")){
            if (event.getMessage().getAuthor().equals("267790240092127232")){
                event.getGuild().getController().kick("267790240092127232");
                event.getChannel().sendMessage("Kicked Mylon - Wat");
            }
        }

    }

    public static class KickCommand extends Command {
        public KickCommand(){
            this.name = "kick";
            this.help = "Kicks @person";
        }

        @Override
        protected void execute(CommandEvent commandEvent) {
            Guild guild = commandEvent.getGuild();

            if (guild == null){
                return;
            }

            Member author = commandEvent.getMessage().getMember();


            //Jimmy not allowed to kick
            if (author.getUser().getId().equals("279117850281639937")){
                commandEvent.reply("No <:tringTring:267794976073711617>");
                return;
            }

            List<Member> mentionedMembers = commandEvent.getMessage().getMentionedMembers();

            if (mentionedMembers.isEmpty()){
                commandEvent.reply("USAGE: $kick @member");
                return;
            }

            if (mentionedMembers.get(0).getUser().getId().equals("265297435390115841")){
                commandEvent.reply("Cannot kick server owner");
                return;
            }

            guild.getController().kick(mentionedMembers.get(0)).queue(success ->{
                commandEvent.reply("Kicked " +mentionedMembers.get(0).getUser().getName() + " <:LUL:434092624492625931>");

            }, error->{
                commandEvent.reply("Unable to kick " +mentionedMembers.get(0).getUser().getName());
            });


        }
    }


    public static class HiCommand extends Command {
        public HiCommand(){
            this.name = "hi";
            this.help = "Says hi";
        }

        @Override
        protected void execute(CommandEvent commandEvent) {

            Member author = commandEvent.getMessage().getMember();

            //Specific users have custom responses
            if (author.getUser().getId().equals("267790240092127232")){
                commandEvent.reply("Hi Mylooo");
            }

            else if (author.getUser().getId().equals("264896877978189835")){
                commandEvent.reply("Hi Navweeb");
            }

            else if (author.getUser().getId().equals("430198257789173761")){
                commandEvent.reply("Hi Prat Ham");
            }

            else {
                if (author.getNickname() == null){
                    commandEvent.reply("Hi " + author.getUser().getName());
                }
                else {
                    commandEvent.reply("Hi " + author.getNickname());
                }
            }

        }
    }

}
