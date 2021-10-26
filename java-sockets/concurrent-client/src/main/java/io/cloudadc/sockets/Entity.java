package io.cloudadc.sockets;

import java.net.Socket;

public class Entity {

        public Entity(int id, Socket s) {
        	setId(id);
            setSrc(s.getLocalSocketAddress().toString());
            setDst(s.getRemoteSocketAddress().toString());
        }
        
        private Integer id;

        private String src;

        private String dst;

        public String getSrc() {
                return src;
        }

        public void setSrc(String src) {
                this.src = src;
        }

        public String getDst() {
                return dst;
        }

        public void setDst(String dst) {
                this.dst = dst;
        }

        public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		@Override
        public String toString() {
                return "[id=" + id + ", src=" + src + ", dst=" + dst + "]";
        }

}