/**
 * This file is part of Aion X Emu <aionxemu.com>
 *
 *  This is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Lesser Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This software is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser Public License
 *  along with this software.  If not, see <http://www.gnu.org/licenses/>.
 */

package quest.relic;

import gameserver.dataholders.DataManager;
import gameserver.model.gameobjects.Npc;
import gameserver.model.gameobjects.player.Player;
import gameserver.model.templates.QuestTemplate;
import gameserver.network.aion.serverpackets.SM_DIALOG_WINDOW;
import gameserver.questEngine.handlers.QuestHandler;
import gameserver.questEngine.model.QuestCookie;
import gameserver.questEngine.model.QuestState;
import gameserver.questEngine.model.QuestStatus;
import gameserver.services.QuestService;
import gameserver.utils.PacketSendUtility;

public class _2834RelicRewardAncientIcon extends QuestHandler {
    private final static int questId = 2834;

    public _2834RelicRewardAncientIcon() {
        super(questId);
    }

    @Override
    public void register() {
        qe.setNpcQuestData(278144).addOnQuestStart(questId); //Byrgafa
        qe.setNpcQuestData(278144).addOnTalkEvent(questId);
    }

    @Override
    public boolean onDialogEvent(QuestCookie env) {
        final Player player = env.getPlayer();
        int targetId = 0;
        if (env.getVisibleObject() instanceof Npc)
            targetId = ((Npc) env.getVisibleObject()).getNpcId();
        QuestTemplate template = DataManager.QUEST_DATA.getQuestById(questId);
        QuestState qs = player.getQuestStateList().getQuestState(questId);
        if (targetId == 278144) {
            if (qs == null || qs.getStatus() == QuestStatus.NONE || (qs.getStatus() == QuestStatus.COMPLETE && (qs.getCompliteCount() <= template.getMaxRepeatCount()))) {
                if (env.getDialogId() == 54) {
                    if (player.getCommonData().getLevel() >= 30) {
                        QuestService.startQuest(env, QuestStatus.START);
                        return sendQuestDialog(env, 1011);
                    } else
                        return sendQuestDialog(env, 3398);
                }
            } else if (qs != null && qs.getStatus() == QuestStatus.START && qs.getQuestVarById(0) == 0) {
                if (env.getDialogId() == -1)
                    return sendQuestDialog(env, 1011);
                else if (env.getDialogId() == 1011) {
                    if (player.getInventory().getItemCountByItemId(186000066) > 0) {
                        player.getInventory().removeFromBagByItemId(186000066, 1);
                        qs.setQuestVar(1);
                        qs.setStatus(QuestStatus.REWARD);
                        qs.setCompliteCount(0);
                        updateQuestStatus(env);
                        return sendQuestDialog(env, 5);
                    } else
                        return sendQuestDialog(env, 1009);
                } else if (env.getDialogId() == 1352) {
                    if (player.getInventory().getItemCountByItemId(186000065) > 0) {
                        player.getInventory().removeFromBagByItemId(186000065, 1);
                        qs.setQuestVar(2);
                        qs.setStatus(QuestStatus.REWARD);
                        qs.setCompliteCount(0);
                        updateQuestStatus(env);
                        return sendQuestDialog(env, 6);
                    } else
                        return sendQuestDialog(env, 1009);
                } else if (env.getDialogId() == 1693) {
                    if (player.getInventory().getItemCountByItemId(186000064) > 0) {
                        player.getInventory().removeFromBagByItemId(186000064, 1);
                        qs.setQuestVar(3);
                        qs.setStatus(QuestStatus.REWARD);
                        qs.setCompliteCount(0);
                        updateQuestStatus(env);
                        return sendQuestDialog(env, 7);
                    } else
                        return sendQuestDialog(env, 1009);
                } else if (env.getDialogId() == 2034) {
                    if (player.getInventory().getItemCountByItemId(186000063) > 0) {
                        player.getInventory().removeFromBagByItemId(186000063, 1);
                        qs.setQuestVar(4);
                        qs.setStatus(QuestStatus.REWARD);
                        qs.setCompliteCount(0);
                        updateQuestStatus(env);
                        return sendQuestDialog(env, 8);
                    } else
                        return sendQuestDialog(env, 1009);
                }
            } else if (qs != null && qs.getStatus() == QuestStatus.REWARD) {
                int var = qs.getQuestVarById(0);
                switch (env.getDialogId()) {
                    case -1:
                        if (var == 1)
                            return sendQuestDialog(env, 5);
                        else if (var == 2)
                            return sendQuestDialog(env, 6);
                        else if (var == 3)
                            return sendQuestDialog(env, 7);
                        else if (var == 4)
                            return sendQuestDialog(env, 8);
                    case 18:
                        QuestService.questFinish(env, qs.getQuestVars().getQuestVars() - 1);
                        PacketSendUtility.sendPacket(player, new SM_DIALOG_WINDOW(env.getVisibleObject().getObjectId(), 10));
                        return true;
                }
            }
        }

        return false;
    }
}
