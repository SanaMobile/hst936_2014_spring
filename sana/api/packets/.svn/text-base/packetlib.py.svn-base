'''
Utilities responsible for reading from incoming packets to reconstruct uploaded
resources as well as issue 

@author: Sana Dev Team
Created on May 31, 2011
'''
import os

class packetizable:
    def __init__(self):
        pass
    
    def __call__(self,*args, **kwargs):
        pass
    
        
MAX_PKT = 1024
MIN_PKT = 64

reader_register = {}
writer_register = {}
    
def register_reader(key, packetizable):
    ''' Creates and registers a new packet reader for the packetizable ''' 
    reader = PacketReader(packetizable)
    if reader.packet_key() in reader_register:
        raise Exception('already registered {0}'.format(reader.packet_key()))
    reader_register[reader.packet_key()] = reader
    return reader

def get_reader(packetizable):
    ''' return the reader for the packetizable '''
    pkt_key = packetizable.packet_key()
    reader = reader_register.get(pkt_key, None)
    if not reader:
        reader = register_reader(pkt_key, packetizable)
    return reader

def remove_reader(packetizable):
    ''' removes the reader for the packetizable '''
    reader_register.pop(packetizable.packet_key())
        
def accept(packetizable, offset, bytes):
    ''' Accept a packet and assembles it into the packetizable '''
    reader = get_reader(packetizable)
    r, msg = reader.accept(offset, bytes, limit=None)
    if r:
        if msg == 'EOF':
            remove_reader(packetizable)
            result = True
        else:
            result = False
    else:
        result = False
    return result

def register_writer(packetizable):
    ''' Creates and registers a new packet writer for the packetizable ''' 
    if packetizable.packet_key() in writer_register:
        raise Exception('already registered {0}'.format(packetizable.packet_key()))
    writer = PacketWriter(packetizable)
    reader_register[writer.packet_key()] = writer
    return writer

def get_writer(packetizable):
    ''' return the writer for the packetizable '''
    pkt_key = packetizable.packet_key()
    writer = writer_register.get(pkt_key, None)
    if not writer:
        writer = register_writer(packetizable)
    return writer

def remove_writer(packetizable):
    ''' removes the writer for the packetizable '''
    writer_register.pop(packetizable.packet_key())
        
def issue(packetizable, inc=False, dec=False, pkt_size=None):
    ''' issue the next packet from a packetizable '''
    writer = get_writer(packetizable)
    r, msg = writer.issue(inc=inc, dec=dec, pkt_size=pkt_size)
    if r:
        if msg == 'EOF':
            remove_writer(packetizable)
            result = True
        else:
            result = False
    else:
        result = False
    return result       

class Packetizable:
    ''' Implementing classes declare that they can be packetized and provide an 
        identifying key 
    '''
    def packet_key(self):
        pass

class PacketReader:
    ''' Class that handles reading packets for reassembly '''
    def __init__(self, binary):
        self.binary = binary
        self.offset = 0
        self.key = (binary.client_uid, binary.encounter_uid, binary.obs_id, binary.uid)
    
    def accept(self, byte_start, byte_data, limit=None):
        """Accept a binary packet and write it to a reconstructed resource.
            binary => binary resource to get the file to write to from
            byte_start => offset from beginning of file to begin writing
            byte_data => an iterable that contains byte_data 
            limit => the max amount of data that can be written. 
        """
        #TODO add max file size in settings
        result = True
        message = 'EOF'
        # raise an exception if start is past end of file or return true with an
        # EOF marker
        if byte_start > self.binary.total_size:
            # TODO use a better exception
            raise Exception
        elif byte_start == self.binary.total_size:
            return result, message  
        
        # Open file
        with open(self.binary.data.path, "r+b") as dest:
            bytes_written = 0
            byte_start = int(byte_start)
            dest.seek(0, os.SEEK_END)
            # seek to byte start marker
            if dest.tell() != byte_start:
                dest.seek(byte_start, os.SEEK_SET)
            # Write bytes and throw if we go past end 
            for chunk in byte_data:
                dest.write(chunk)
                bytes_written += len(chunk)
                if limit and dest.tell() > limit:
                    # TODO use a better exception
                    raise Exception
                if dest.tell() > self.binary.total_size:
                    # TODO use a better exception
                    raise Exception
            dest.flush()
            if dest.tell() > self.binary.upload_progress:
                self.binary.upload_progress = dest.tell()
                self.binary.save()
            # EOF check
            if self.binary.upload_progress < self.binary.total_size:
                result = False
                #TODO Rethink how we want to return the result?
                message = dest.tell()
        return result, message

    def packet_key(self):
        ''' The key for this readers packetizable '''
        return self.packetizable.packet_key

class PacketWriter:
    ''' Class that handles issuing packets from a byte source. '''
    def __init__(self, binary):
        self.binary = binary
        self.offset = 0
        self.pkt_size = MAX_PKT
        self.key = (binary.client_uid, binary.encounter_uid, binary.obs_id, binary.uid)
    
    def issue(self, inc=False, dec=False, pkt_size=None):
        # TODO: proof this
        '''
        Issues a series of packet data from a resource:
        
            inc => set True to increase the packet size
            dec => set True to decrease the packet size
            pkt_size => manually sets the size of the packet to issue
        
        Note order of precedence for setting packet size is 
        
            pkt_size -> inc -> dec
        
        Incrementing and decrementing will increase or decrease the packet size by a
        factor of 2 to MAX_PKT or MIN_PKT respectively 
        '''
        #TODO finish implementing this
        bytes = None
        with open(self.binary.data.path, "r+b") as source:
            source.seek(0, os.SEEK_END)
            while self.offset < self.binary.total_size:
                if pkt_size:
                    self.pkt_size = pkt_size
                elif inc:
                    new_size = self.pkt_size * 2
                    self.pkt_size = new_size if new_size <= MAX_PKT else MAX_PKT
                elif dec:
                    new_size = int(self.pkt_size / 2)
                    self.pkt_size = new_size if new_size >= MIN_PKT else MIN_PKT
                # limit size if greater than remaining 
                if (self.binary.total_size - self.offset) < self.pkt_size:
                    self.pkt_size = self.binary.total_size - self.offset
                #bytes = source.read() 
                yield bytes


    def packet_key(self):
        ''' The key for this writer's packetizable '''
        return self.packetizable.packet_key