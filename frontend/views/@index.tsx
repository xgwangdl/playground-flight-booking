import {useEffect, useState} from "react";
import {AssistantService, BookingService} from "Frontend/generated/endpoints";
import BookingDetails from "../generated/ai/spring/demo/ai/playground/services/BookingTools/BookingDetails";
import {GridColumn} from "@vaadin/react-components/GridColumn";
import {Grid} from "@vaadin/react-components/Grid";
import {MessageInput} from "@vaadin/react-components/MessageInput";
import {nanoid} from "nanoid";
import {SplitLayout} from "@vaadin/react-components/SplitLayout";
import Message, {MessageItem} from "../components/Message";
import MessageList from "Frontend/components/MessageList";

export default function Index() {
  const [chatId, setChatId] = useState(nanoid());
  const [working, setWorking] = useState(false);
  const [bookings, setBookings] = useState<BookingDetails[]>([]);
  const [messages, setMessages] = useState<MessageItem[]>([{
    role: 'assistant',
    content: '欢迎您莅临光哥航空! 有什么可以效劳的么?'
  }]);

  useEffect(() => {
    // Update bookings when we have received the full response
    if (!working) {
      BookingService.getBookings().then(setBookings);
    }
  }, [working]);

  function addMessage(message: MessageItem) {
    setMessages(messages => [...messages, message]);
  }

  function appendToLatestMessage(chunk: string) {
    setMessages(messages => {
      const latestMessage = messages[messages.length - 1];
      latestMessage.content += chunk;
      return [...messages.slice(0, -1), latestMessage];
    });
  }

  async function sendMessage(message: string) {
    setWorking(true);
    addMessage({
      role: 'user',
      content: message
    });
    let first = true;
    AssistantService.chat(chatId, message)
      .onNext(token => {
        if (first && token) {
          addMessage({
            role: 'assistant',
            content: token
          });

          first = false;
        } else {
          appendToLatestMessage(token);
        }
      })
      .onError(() => setWorking(false))
      .onComplete(() => setWorking(false));
  }

  return (
    <SplitLayout className="h-full">
      <div className="flex flex-col gap-m p-m box-border h-full" style={{width: '30%'}}>
        <h3>光哥航空</h3>
        <MessageList messages={messages} className="flex-grow overflow-scroll msgp"/>
        <MessageInput onSubmit={e => sendMessage(e.detail.value)}  className="px-0" disabled={working} />
      </div>
      <div className="flex flex-col gap-m p-m box-border" style={{width: '70%'}}>
        <h3>订单信息</h3>
        <Grid items={bookings} className="flex-shrink-0">
          <GridColumn path="bookingNumber" header="订单号" autoWidth/>
          <GridColumn path="name" header="姓名" autoWidth/>
          <GridColumn path="date" header="出发日期" autoWidth/>
          <GridColumn path="from" header="出发地" autoWidth/>
          <GridColumn path="to" header="目的地" autoWidth/>
          <GridColumn path="bookingStatus" header="订单状态" autoWidth >
            {({item}) => item.bookingStatus === "CONFIRMED" ? "✅" : "❌"}
          </GridColumn>
          <GridColumn path="bookingClass" header="机舱等级" autoWidth/>
        </Grid>
      </div>
    </SplitLayout>

  );
}